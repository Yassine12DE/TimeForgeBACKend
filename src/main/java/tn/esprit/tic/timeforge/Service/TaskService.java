package tn.esprit.tic.timeforge.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.timeforge.Entity.*;
import tn.esprit.tic.timeforge.Entity.Ennum.RoleName;
import tn.esprit.tic.timeforge.Entity.Ennum.StatusTask;
import tn.esprit.tic.timeforge.Repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor

public class TaskService implements IntTaskService {
   @Autowired
    TaskRepository taskRepository;
    @Autowired
    IprojetRepo iprojetRepo;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RewardRepository rewardRepository;

    @Override
    public Task addTask(Task task ,Long idprojet,Long employeeId,Long lead) {
        Project project = iprojetRepo.findById(idprojet).orElse(null);
        task.setProject(project);
        User user = userRepository.findById(employeeId).orElse(null);
        User leaduser = userRepository.findById(lead).orElse(null);
        task.setEmployee11(user);
        task.setTeamleader(leaduser);
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
          taskRepository.deleteById(id );
    }

    @Override
    public Task updateTask(Long taskId, Task updatedTask) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // Update task fields
        existingTask.setName(updatedTask.getName());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setDeadline(updatedTask.getDeadline());
        existingTask.setStatus(updatedTask.getStatus());

        Task savedTask = taskRepository.save(existingTask);


        if ("DONE".equalsIgnoreCase(String.valueOf(savedTask.getStatus()))
                && savedTask.getDeadline() != null
                && savedTask.getDeadline().isAfter(LocalDate.now())) {
            applyReward(savedTask);
            User employee = savedTask.getEmployee11();

            if (employee != null) {
                //  formula: reward = (level * 10) + (streak * 5)
                int rewardAmount = (employee.getLevel() * 10) + (employee.getStreak() * 5);

                Reward reward = new Reward();
                reward.setRewardDate(LocalDate.now());
                reward.setRewardAmount(rewardAmount);
                reward.setEmployee(employee);
                reward.setTask(savedTask);

                rewardRepository.save(reward);
            }
        }

        return savedTask;
    }

    private void applyReward(Task task) {
        User employee = task.getEmployee11();

        // Check deadline vs completion
        if (task.getDeadline() != null && task.getUpdatedAt() != null) {
            if (task.getUpdatedAt().isAfter(task.getDeadline().atStartOfDay())) {
                // Late → reset streak
                employee.setStreak(0);
            } else {
                // On time → increase streak
                employee.setStreak(employee.getStreak() + 1);
            }
        }

        int baseCredit = task.getTaskCredit();
        // base XP + streak bonus (20% per streak step)
        int gainedXp = baseCredit + (int) Math.round(baseCredit * (employee.getStreak() * 0.2));

        int xp = employee.getXp() + gainedXp;
        int level = employee.getLevel();
        int xpNeeded = level * 50;

        // Handle multiple level-ups if XP is very high
        while (xp >= xpNeeded) {
            xp -= xpNeeded;
            level++;
            xpNeeded = level * 50;
        }

        employee.setXp(xp);
        employee.setLevel(level);

        User updatedEmployee = userRepository.save(employee);
        task.setEmployee11(updatedEmployee);


    }


    @Override
    @Transactional
    public String updateTaskStatus(Long id, StatusTask updatedStatus) {
        Task task = taskRepository.findById(id).orElse(null);

        if (task == null) {
            return "❌ Task not found.";
        }

        StatusTask currentStatus = task.getStatus();

        if (currentStatus == StatusTask.DONE && updatedStatus == StatusTask.TODO) {
            return "⛔ Cannot change status from DONE back to TODO.";
        }

        StringBuilder message = new StringBuilder();
        message.append("✅ Status changed from ").append(currentStatus).append(" to ").append(updatedStatus).append(".\n");

        task.setStatus(updatedStatus);

        if (updatedStatus == StatusTask.DONE) {
            if (task.getDeadline() != null && task.getDeadline().isBefore(LocalDate.now())) {
                message.append("⏰ Tâche terminée après la deadline (").append(task.getDeadline()).append(").\n");
            } else {
                message.append("✅ Tâche terminée à temps par rapport à la deadline.\n");
            }

            if (task.getStartDate() != null) {
                long actualDays = ChronoUnit.DAYS.between(task.getStartDate(), LocalDate.now());
                long actualHours = actualDays * 8; // Exemple : 8h par jour

                message.append("📊 Durée réelle estimée : ").append(actualHours).append("h. Estimation initiale : ")
                        .append(task.getEstimatedHours()).append("h.\n");

                if (task.getEstimatedHours() > 0 && actualHours > task.getEstimatedHours()) {
                    message.append("⚠️ Estimation dépassée de ").append(actualHours - task.getEstimatedHours()).append(" heures.\n");
                } else {
                    message.append("🕒 Tâche réalisée dans le temps estimé.\n");
                }
            }
        }

        taskRepository.save(task);

        return message.toString();
    }




    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with ID " + id + " not found"));

    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    @Override
    public List<Task> getAllTasksbyUser(Long id) {
        User user = userRepository.findById(id).orElse(null);

        return taskRepository.findAllByEmployee11(user);
    }
    @Override
    public Set<User> getAllUSER() {
        Role role = roleRepository.findByName(RoleName.ROLE_EMPLOYEE).get();
        return userRepository.findAllByRoles(role);
    }
    @Override
    public List<Project> getall() {
        return iprojetRepo.findAll();
    }
    @Override
    public List<Project> getallprojetbyidLead(Long id) {
        System.out.println(id);
        User user = userRepository.findById(id).orElse(null);
        return iprojetRepo.findAllByTeamlead(user);
    }

}
