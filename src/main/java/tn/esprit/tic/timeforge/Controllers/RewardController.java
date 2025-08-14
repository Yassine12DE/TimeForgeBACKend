package tn.esprit.tic.timeforge.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tic.timeforge.Service.RewardService;
import tn.esprit.tic.timeforge.dto.RewardHistoryDTO;
import tn.esprit.tic.timeforge.dto.UserRewardCountDTO;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/rewards")
public class RewardController {
    private final RewardService rewardService;

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<RewardHistoryDTO>> getRewardHistory (@PathVariable Long userId){
        List<RewardHistoryDTO> history = rewardService.getRewardHistoryUserID(userId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/top-rewarded-users")
    public List<UserRewardCountDTO> getTopRewardedUsers() {
        return rewardService.getTop15RewardedUsers();
    }
}
