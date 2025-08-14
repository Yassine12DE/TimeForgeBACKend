package tn.esprit.tic.timeforge.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.timeforge.Entity.Project;
import tn.esprit.tic.timeforge.Entity.Task;
import tn.esprit.tic.timeforge.Entity.User;
import tn.esprit.tic.timeforge.Service.IntTaskService;

import java.util.List;
import java.util.Set;


@AllArgsConstructor
@RestController
@RequestMapping("tasks")

public class TaskController {
    @Autowired
    IntTaskService intTaskService;

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task,@RequestParam Long id,@RequestParam(value = "employeeid") Long employeeid,@RequestParam(value = "lead") Long lead){
        return  ResponseEntity.status(201).body(intTaskService.addTask(task,id,employeeid,lead));
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        intTaskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/tasksbyuser/{id}")
    public List<Task> getalltaskbyuser(@PathVariable Long id) {

        return intTaskService.getAllTasksbyUser(id);
    }
    @GetMapping("/projetsbyuser/{id}")
    public List<Project> getallprojetbyuser(@PathVariable Long id) {
        return intTaskService.getallprojetbyidLead(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {

            Task task = intTaskService.updateTask(id, updatedTask);
            return new ResponseEntity<>(task, HttpStatus.OK);

    }
    @PutMapping("/status/{id}")
    public ResponseEntity<String> updateTaskStatus(@PathVariable Long id, @RequestBody Task updatedTask) {


        return new ResponseEntity<>(intTaskService.updateTaskStatus(id, updatedTask.getStatus()), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = intTaskService.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = intTaskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/allproject")
    public ResponseEntity<List<Project>> getAll() {
        List<Project> p = intTaskService.getall();
        return new ResponseEntity<>(p, HttpStatus.OK);
    }
    @GetMapping("/allUsers")
    public ResponseEntity<Set<User>> getAllUser() {
        Set<User> p = intTaskService.getAllUSER();
        return new ResponseEntity<>(p, HttpStatus.OK);
    }
}

