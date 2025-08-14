package tn.esprit.tic.timeforge.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.timeforge.Entity.Goals;
import tn.esprit.tic.timeforge.Entity.Task;
import tn.esprit.tic.timeforge.Repository.GoalsRepository;
import tn.esprit.tic.timeforge.Repository.TaskRepository;
import tn.esprit.tic.timeforge.Service.GoalsService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/goals")
public class GoalsController {

    @Autowired
    GoalsService goalsService;

    @Autowired
    GoalsRepository goalsRepository;

    @Autowired
    TaskRepository taskRepository;

    // ✅ Activation de la validation en ajoutant @Valid
    @PostMapping("add/{TaskId}")
    public ResponseEntity<Goals> ajout(@Valid @RequestBody Goals goals, @PathVariable long TaskId) {
        Goals savedGoal = goalsService.addGoalAndAssignToTask(goals, TaskId);
        return ResponseEntity.ok(savedGoal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {
        goalsService.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Goals> getGoalById(@PathVariable Long id) {
        Goals goal = goalsService.getGoalById(id);
        return goal != null ? ResponseEntity.ok(goal) : ResponseEntity.notFound().build();
    }


    @GetMapping
    public ResponseEntity<List<Goals>> getAllGoals() {
        return ResponseEntity.ok(goalsService.getAllGoals());
    }

    @GetMapping("goalbytaks/{taskid}")
    public List<Goals> getgoalsbytaskid(@PathVariable long taskid) {
        return goalsRepository.findGoalsByTask13_Id(taskid);
    }

    @GetMapping("/{id}/with-goals")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskRepository.findById(id).orElse(null));
    }

    @GetMapping("/taksbygoals")
    public List<Task> Retreivealltasks() {
        return taskRepository.findAll();
    }
    @GetMapping("/next")
    public ResponseEntity<String> predictNextGoal() {
        try {
            ProcessBuilder pb = new ProcessBuilder("python", "ml/predict_next_goal.py");
            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.lines().collect(Collectors.joining("\n"));
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                return ResponseEntity.ok(output);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur dans le script IA : " + output);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la prédiction : " + e.getMessage());
        }
    }

}

