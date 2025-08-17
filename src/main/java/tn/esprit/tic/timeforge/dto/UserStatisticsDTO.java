package tn.esprit.tic.timeforge.dto;



import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserStatisticsDTO {
    private Long userId;
    private String username;
    private int level;
    private int xp;
    private int xpToNextLevel;
    private int streak;
    private int totalTasks;
    private int completedTasks;
    private int onTimeTasks;
    private int lateTasks;
    private int totalRewards;
    private int rewardPoints;

    // For charts
    private List<Integer> weeklyTaskCompletion; // tasks done each day of current week
    private List<Integer> rewardHistory;        // rewards over last N tasks/days
}
