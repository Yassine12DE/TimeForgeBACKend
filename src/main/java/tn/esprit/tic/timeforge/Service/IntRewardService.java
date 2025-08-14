package tn.esprit.tic.timeforge.Service;

import tn.esprit.tic.timeforge.dto.RewardHistoryDTO;

import java.time.LocalDate;
import java.util.List;

public interface IntRewardService {
    void assignReward(LocalDate today);
    void autoAssign();
    List<RewardHistoryDTO> getRewardHistoryUserID(Long idUser);
}
