package tn.esprit.tic.timeforge.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.timeforge.Entity.StrategicReminder;
import tn.esprit.tic.timeforge.Repository.StrategicReminderRepository;
import tn.esprit.tic.timeforge.Service.StrategicReminderService;
import tn.esprit.tic.timeforge.Service.StrategicReminderServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/strategic-reminders")
public class StrategicReminderController {

    @Autowired
    private StrategicReminderRepository strategicReminderRepository;

    @Autowired
    private StrategicReminderServiceImpl reminderService;



    @GetMapping("/reminders")
    public List<StrategicReminder> Retreivealltasks() {
        return strategicReminderRepository.findAll();
    }



    @DeleteMapping("/reminders/{id}")
    public ResponseEntity<Void> deleteReminder(@PathVariable Long id) {
        boolean deleted = reminderService.deleteReminder(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

