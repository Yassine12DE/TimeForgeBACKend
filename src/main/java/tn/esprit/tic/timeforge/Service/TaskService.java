package tn.esprit.tic.timeforge.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.timeforge.Entity.Ennum.RoleName;
import tn.esprit.tic.timeforge.Entity.Ennum.StatusTask;
import tn.esprit.tic.timeforge.Entity.Project;
import tn.esprit.tic.timeforge.Entity.Role;
import tn.esprit.tic.timeforge.Entity.Task;
import tn.esprit.tic.timeforge.Entity.User;
import tn.esprit.tic.timeforge.Repository.IprojetRepo;
import tn.esprit.tic.timeforge.Repository.RoleRepository;
import tn.esprit.tic.timeforge.Repository.TaskRepository;
import tn.esprit.tic.timeforge.Repository.UserRepository;

import java.time.LocalDate;
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
    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id)
                .map(existingTask -> {
                    existingTask.setName(updatedTask.getName());
                    existingTask.setDescription(updatedTask.getDescription());

                    existingTask.setStatus(updatedTask.getStatus());
                    return taskRepository.save(existingTask);
                })
                .orElseThrow(() -> new EntityNotFoundException("Task with ID " + id + " not found"));
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
