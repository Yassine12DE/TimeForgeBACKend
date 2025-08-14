package tn.esprit.tic.timeforge.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.Entity.Ennum.TypeReminder;
import tn.esprit.tic.timeforge.Entity.Goals;
import tn.esprit.tic.timeforge.Entity.HealthReminder;
import tn.esprit.tic.timeforge.Repository.GoalsRepository;
import tn.esprit.tic.timeforge.Repository.HealthReminderRepository;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class HealthReminderServiceImpl implements HealthReminderService {

    @Autowired
    HealthReminderRepository healthReminderRepository;
    @Autowired
    GoalsRepository goalsRepository;

    @Override
    public HealthReminder addHealthReminder(HealthReminder healthReminder) {
        return healthReminderRepository.save(healthReminder);
    }

    @Override
    public HealthReminder updateHealthReminder(Long id, HealthReminder healthReminder) {
        HealthReminder existingReminder = healthReminderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("HealthReminder not found with ID: " + id));

        existingReminder.setTypeReminder(healthReminder.getTypeReminder());
        existingReminder.setNotifications(healthReminder.getNotifications());

        return healthReminderRepository.save(existingReminder);
    }

    @Override
    public void deleteHealthReminder(Long id) {
        if (!healthReminderRepository.existsById(id)) {
            throw new IllegalArgumentException("Cannot delete: HealthReminder with ID " + id + " not found");
        }
        healthReminderRepository.deleteById(id);
    }

    @Override
    public HealthReminder getHealthReminderById(Long id) {
        return healthReminderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("HealthReminder not found with ID: " + id));
    }

    @Override
    public List<HealthReminder> getAllHealthReminders() {
        return healthReminderRepository.findAll();
    }

    @Override
    public void toggleChronicMode(Long goalId, boolean activate) {
        Goals goal = goalsRepository.findById(goalId).orElseThrow(() -> new RuntimeException("Goal not found"));
        goal.setChronicActive(activate);
        goalsRepository.save(goal);
    }
    @Override
    @Scheduled(fixedRate = 15000) // 1h30 en millisecondes
    public void createHealthReminders() {
        List<Goals> activeGoals = goalsRepository.findByChronicActiveTrue();

        for (Goals goal : activeGoals) {
            HealthReminder reminder = new HealthReminder();
            reminder.setGoal(goal);
            reminder.setTypeReminder(TypeReminder.HELTHBREAKS);
            healthReminderRepository.save(reminder);
        }
    }
}