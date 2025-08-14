package tn.esprit.tic.timeforge.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.Entity.Goals;
import tn.esprit.tic.timeforge.Entity.Task;
import tn.esprit.tic.timeforge.Entity.User;
import tn.esprit.tic.timeforge.Repository.IprojetRepo;
import tn.esprit.tic.timeforge.Repository.RewardRepository;
import tn.esprit.tic.timeforge.Repository.TaskRepository;
import tn.esprit.tic.timeforge.Repository.UserRepository;
import tn.esprit.tic.timeforge.dto.DashboardStats;
import tn.esprit.tic.timeforge.dto.UserProductivity;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DashboardService implements IntDashboardService{

    private final UserRepository userRepository;
    private final RewardRepository rewardRepository;
    private final TaskRepository taskRepository;
    private final IprojetRepo iprojetRepo;

    @Override
    public List<UserProductivity> getUserProductivity() {
        List<User> users = userRepository.findAll();
        List<UserProductivity> productivityList = new ArrayList<>();

        for (User user : users) {
            int totalGoals = 0;
            int completedGoals = 0;
            int tasksCompletedOnTime = 0;

            List<Task> tasks = taskRepository.findAllByEmployee11(user);


            for (Task task : tasks) {
                List<Goals> goals = task.getGoals();
                totalGoals +=goals.size();//ehsebli qadeh mn goal aandou

                for (Goals goal : goals){
                    if ("CompletedPending".equalsIgnoreCase(goal.getStatusGoal().toString())){
                        completedGoals++; // ehsebli lgoals
                    }
                }

            }


            double goalCompletionPercentage = totalGoals == 0 ? 0 : (double) completedGoals / totalGoals * 100;
            tasksCompletedOnTime = rewardRepository.countByEmployee_IdUser(user.getIdUser());

            UserProductivity dto = new UserProductivity();
            dto.setUserName(user.getName()); //hot eesmou lena
            dto.setTotalGoals(totalGoals);//Qadeh mn goal aandou
            dto.setCompletedGoals(completedGoals);//Qadeh mn goal kamel
            dto.setGoalCompletionPercentage(goalCompletionPercentage); // percentage
            dto.setTasksCompletedOnTime(tasksCompletedOnTime);// Qadeh mn task kamlou fl waqet

            productivityList.add(dto);
        }

        return productivityList;
    }
    public UserProductivity getUserProductivityById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        int totalGoals = 0;
        int completedGoals = 0;
        int tasksCompletedOnTime = 0;

        List<Task> tasks = taskRepository.findAllByEmployee11(user);

        for (Task task : tasks) {
            List<Goals> goals = task.getGoals();
            totalGoals += goals.size();

            for (Goals goal : goals) {
                if ("CompletedPending".equalsIgnoreCase(goal.getStatusGoal().toString())) {
                    completedGoals++;
                }
            }
        }

        double goalCompletionPercentage = totalGoals == 0 ? 0 : (double) completedGoals / totalGoals * 100;
        tasksCompletedOnTime = rewardRepository.countByEmployee_IdUser(user.getIdUser());

        UserProductivity dto = new UserProductivity();
        dto.setUserName(user.getName());
        dto.setTotalGoals(totalGoals);
        dto.setCompletedGoals(completedGoals);
        dto.setGoalCompletionPercentage(goalCompletionPercentage);
        dto.setTasksCompletedOnTime(tasksCompletedOnTime);

        return dto;
    }
    public DashboardStats getDashboardStats() {
        long userCount = userRepository.count();
        long projectCount = iprojetRepo.count();
        long rewardCount = rewardRepository.count(); // or rewardRepository.countByX if needed

        return new DashboardStats(userCount, projectCount, rewardCount);
    }




}
