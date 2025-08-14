package tn.esprit.tic.timeforge.Service;

import tn.esprit.tic.timeforge.Entity.Goals;
import tn.esprit.tic.timeforge.Entity.Task;

import java.util.List;

public interface GoalsService {
    Goals addGoal(Goals goal);

//    Goals updateGoal(Long id, Goals goal);

    void deleteGoal(Long id);

    Goals getGoalById(Long id);

    List<Goals> getAllGoals();

   Goals addGoalAndAssignToTask(Goals goal, long taskId);
}
