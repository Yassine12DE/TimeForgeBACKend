package tn.esprit.tic.timeforge.Controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.timeforge.Entity.User;
import tn.esprit.tic.timeforge.Service.AdminService;
import tn.esprit.tic.timeforge.Service.DashboardService;
import tn.esprit.tic.timeforge.dto.DashboardStats;

import java.util.List;
import java.util.Set;

@RestController

@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;


    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }



    @PutMapping("/users/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User userDetails) {
        User updatedUser = adminService.updateUser(userId, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }
    
    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getbyid(@PathVariable Long userId) {
        User user =adminService.getbyid(userId);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = adminService.listUsers();
        return ResponseEntity.ok(users);
    }
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats")
    public DashboardStats getStats() {
        return dashboardService.getDashboardStats();
    }



}

