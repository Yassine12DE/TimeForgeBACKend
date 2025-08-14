package tn.esprit.tic.timeforge.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.Entity.Ennum.StatusTask;
import tn.esprit.tic.timeforge.Entity.Reward;
import tn.esprit.tic.timeforge.Entity.Task;
import tn.esprit.tic.timeforge.Entity.User;
import tn.esprit.tic.timeforge.Repository.RewardRepository;
import tn.esprit.tic.timeforge.Repository.TaskRepository;
import tn.esprit.tic.timeforge.Repository.UserRepository;
import tn.esprit.tic.timeforge.dto.RewardHistoryDTO;
import tn.esprit.tic.timeforge.dto.UserRewardCountDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class RewardService implements IntRewardService{

    @Autowired
    private EmailRewardService emailRewardService;

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void assignReward(LocalDate today) {
        List<User> users = userRepository.findAll(); // Het l'users lkol
        for (User user : users){
            List<Task> tasks = taskRepository.findAllByEmployee11(user);
            for (Task task : tasks){
                if (task.getStatus() == StatusTask.DONE  &&
                        task.getUpdatedAt().isBefore(task.getDeadline().plusDays(1).atStartOfDay()) &&
                        task.getUpdatedAt().toLocalDate().isEqual(today)){
                    Reward reward = new Reward();
                    reward.setRewardDate(LocalDate.now());
                    reward.setTask(task);
                    reward.setEmployee(user);
                    rewardRepository.save(reward);
                    emailRewardService.SendRewardEmail(user);

                }

            }
        }

    }


    @Scheduled(cron = "0 05 16 * * ?")

    @Override
    public void autoAssign() {
        LocalDate today = LocalDate.now();
        assignReward(today);

    }

    @Override
    public List<RewardHistoryDTO> getRewardHistoryUserID(Long idUser) {
        List<Reward> rewards = rewardRepository.findByEmployee_IdUser(idUser);
        Integer totlaRewards = rewards.size();
        return rewards.stream()
                .map(reward -> new RewardHistoryDTO(
                        "Deadline Champion",
                        reward.getRewardDate(),
                        totlaRewards
                ))
                .collect(Collectors.toList());
    }


    public List<UserRewardCountDTO> getTop15RewardedUsers() {
        return rewardRepository.findTopRewardedUsers(PageRequest.of(0, 15));
    }


}
