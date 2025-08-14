package tn.esprit.tic.timeforge.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.Entity.Goals;
import tn.esprit.tic.timeforge.Entity.StrategicReminder;
import tn.esprit.tic.timeforge.Repository.GoalsRepository;
import tn.esprit.tic.timeforge.Repository.StrategicReminderRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StrategicReminderServiceImpl  {

    @Autowired
    private GoalsRepository goalsRepository;

    @Autowired
    private StrategicReminderRepository strategicReminderRepository;

    @Scheduled(cron = "*/15 * * * * *")
    public void checkGoalEndDatesAndSendReminders() {
        List<Goals> goals = goalsRepository.findAll();

        Date currentDate = new Date();

        for (Goals goal : goals) {
            if (goal.getEndDate() != null && !goal.isReminderSent()) {
                long timeDifference = goal.getEndDate().getTime() - currentDate.getTime();
                long daysDifference = timeDifference / (1000 * 60 * 60 * 24);

                if (daysDifference == 1) {
                    StrategicReminder reminder = new StrategicReminder();
                    reminder.setTitle("Goal Deadline Reminder");
                    reminder.setDescription("Your goal \"" + goal.getTitle() + "\" is ending in 1 days.");
                    reminder.setGoal(goal);

                    goal.setReminderSent(true);
                    goalsRepository.save(goal);

                    strategicReminderRepository.save(reminder);

                    System.out.println("Reminder sent for goal: " + goal.getTitle());
                }
            }
        }
    }

    @Autowired
    private StrategicReminderRepository reminderRepository;


    public boolean deleteReminder(Long id) {
        if (reminderRepository.existsById(id)) {
            reminderRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
