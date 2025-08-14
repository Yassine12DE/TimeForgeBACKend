package tn.esprit.tic.timeforge.Controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tic.timeforge.Service.DashboardService;
import tn.esprit.tic.timeforge.dto.UserProductivity;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;
    //@PreAuthorize("HasRole("")")
    @GetMapping("/users-productivity")
    public ResponseEntity<List<UserProductivity>> getAllUserProductivity(){
        return ResponseEntity.ok(dashboardService.getUserProductivity());
    }
    @GetMapping("/user-productivity/{id}")
    public ResponseEntity<UserProductivity> getUserProductivityById(@PathVariable Long id) {
        return ResponseEntity.ok(dashboardService.getUserProductivityById(id));
    }
}
