package tn.esprit.tic.timeforge.Controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.timeforge.dto.UserStatisticsDTO;
import tn.esprit.tic.timeforge.Service.UserStatisticsService;

@RestController
@RequestMapping("statistics")
@RequiredArgsConstructor

public class UserStatisticsController {

    private final UserStatisticsService userStatisticsService;

    @GetMapping("/user/{userId}")
    public UserStatisticsDTO getUserStatistics(@PathVariable Long userId) {
        return userStatisticsService.getUserStatistics(userId);
    }
}
