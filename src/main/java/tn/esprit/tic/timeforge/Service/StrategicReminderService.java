package tn.esprit.tic.timeforge.Service;

import tn.esprit.tic.timeforge.Entity.StrategicReminder;

import java.util.List;

public interface StrategicReminderService {
    StrategicReminder addStrategicReminder(StrategicReminder strategicReminder);
    StrategicReminder updateStrategicReminder(Long id, StrategicReminder strategicReminder);
    void deleteStrategicReminder(Long id);
    StrategicReminder getStrategicReminderById(Long id);
    List<StrategicReminder> getAllStrategicReminders();
}
