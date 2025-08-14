package tn.esprit.tic.timeforge.Entity;

import lombok.Data;

@Data
public class UserPerformance {
    private String date;
    private int tasksCompleted;
    private int tasksPlanned;
    private double completionRate;
    private double efficiency;
    private double focusTime;
}
