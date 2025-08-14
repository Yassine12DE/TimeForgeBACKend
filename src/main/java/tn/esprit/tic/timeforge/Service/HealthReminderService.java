package tn.esprit.tic.timeforge.Service;

import tn.esprit.tic.timeforge.Entity.HealthReminder;

import java.util.List;

public interface HealthReminderService {
    HealthReminder addHealthReminder(HealthReminder healthReminder);
    HealthReminder updateHealthReminder(Long id, HealthReminder healthReminder);
    void deleteHealthReminder(Long id);

    HealthReminder getHealthReminderById(Long id);
    List<HealthReminder> getAllHealthReminders();
    void createHealthReminders();
    void toggleChronicMode(Long goalId, boolean activate);
}
