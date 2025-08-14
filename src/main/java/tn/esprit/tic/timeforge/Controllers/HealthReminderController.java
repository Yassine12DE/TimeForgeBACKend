package tn.esprit.tic.timeforge.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.timeforge.Entity.HealthReminder;
import tn.esprit.tic.timeforge.Service.HealthReminderService;


import java.util.List;

@RestController
@RequestMapping("/api/health-reminders")
public class HealthReminderController {

    @Autowired
    HealthReminderService healthReminderService;

    @PostMapping
    public ResponseEntity<HealthReminder> addHealthReminder(@RequestBody HealthReminder healthReminder) {
        return ResponseEntity.ok(healthReminderService.addHealthReminder(healthReminder));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HealthReminder> updateHealthReminder(@PathVariable Long id, @RequestBody HealthReminder healthReminder) {
        HealthReminder updatedReminder = healthReminderService.updateHealthReminder(id, healthReminder);
        return updatedReminder != null ? ResponseEntity.ok(updatedReminder) : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHealthReminder(@PathVariable Long id) {
        healthReminderService.deleteHealthReminder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HealthReminder> getHealthReminderById(@PathVariable Long id) {
        HealthReminder reminder = healthReminderService.getHealthReminderById(id);
        return reminder != null ? ResponseEntity.ok(reminder) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<HealthReminder>> getAllHealthReminders() {
        return ResponseEntity.ok(healthReminderService.getAllHealthReminders());
    }

    @PutMapping("/{goalId}/chronic/{activate}")
    public String toggleChronic(@PathVariable Long goalId, @PathVariable boolean activate) {
        healthReminderService.toggleChronicMode(goalId, activate);
        return "Mode chronique " + (activate ? "activé" : "désactivé") + " pour l'objectif ID " + goalId;
    }
}
