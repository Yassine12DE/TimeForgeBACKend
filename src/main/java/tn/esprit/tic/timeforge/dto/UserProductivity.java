package tn.esprit.tic.timeforge.dto;



import lombok.Data;


@Data
public class UserProductivity {
    private String userName;
    private int totalGoals;
    private int completedGoals;
    private double goalCompletionPercentage;
    private int tasksCompletedOnTime;
}
