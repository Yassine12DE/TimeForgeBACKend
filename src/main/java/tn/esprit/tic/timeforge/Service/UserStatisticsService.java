package tn.esprit.tic.timeforge.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.Entity.Task;
import tn.esprit.tic.timeforge.Entity.User;
import tn.esprit.tic.timeforge.Entity.Reward;
import tn.esprit.tic.timeforge.Repository.TaskRepository;
import tn.esprit.tic.timeforge.Repository.UserRepository;
import tn.esprit.tic.timeforge.Repository.RewardRepository;
import tn.esprit.tic.timeforge.dto.UserStatisticsDTO;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserStatisticsService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final RewardRepository rewardRepository;

    public UserStatisticsDTO getUserStatistics(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Task> tasks = taskRepository.findByEmployee11_IdUser(userId);
        List<Reward> rewards = rewardRepository.findByEmployee_IdUser(userId);

        int totalTasks = tasks.size();
        int completedTasks = (int) tasks.stream().filter(t -> "DONE".equalsIgnoreCase(t.getStatus().name())).count();
        int onTimeTasks = (int) tasks.stream()
                .filter(t -> "DONE".equalsIgnoreCase(t.getStatus().name())
                        && t.getDeadline() != null
                        && t.getUpdatedAt() != null
                        && !t.getUpdatedAt().isAfter(t.getDeadline().atStartOfDay()))
                .count();
        int lateTasks = completedTasks - onTimeTasks;

        int totalRewards = rewards.size();
        int rewardPoints = rewards.stream().mapToInt(Reward::getRewardAmount).sum();

        // XP needed for next level
        int xpToNextLevel = user.getLevel() * 50 - user.getXp();

        // Weekly completion chart
        Map<LocalDate, Long> completedPerDay = tasks.stream()
                .filter(t -> t.getUpdatedAt() != null && "DONE".equalsIgnoreCase(t.getStatus().name()))
                .collect(Collectors.groupingBy(t -> t.getUpdatedAt().toLocalDate(), Collectors.counting()));

        List<Integer> weeklyTaskCompletion = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            LocalDate day = today.minusDays(i);
            weeklyTaskCompletion.add(completedPerDay.getOrDefault(day, 0L).intValue());
        }

        // Reward history (last 10 rewards)
        List<Integer> rewardHistory = rewards.stream()
                .sorted(Comparator.comparing(Reward::getRewardDate).reversed())
                .limit(10)
                .map(Reward::getRewardAmount)
                .collect(Collectors.toList());

        return UserStatisticsDTO.builder()
                .userId(user.getIdUser())
                .username(user.getName())
                .level(user.getLevel())
                .xp(user.getXp())
                .xpToNextLevel(xpToNextLevel)
                .streak(user.getStreak())
                .totalTasks(totalTasks)
                .completedTasks(completedTasks)
                .onTimeTasks(onTimeTasks)
                .lateTasks(lateTasks)
                .totalRewards(totalRewards)
                .rewardPoints(rewardPoints)
                .weeklyTaskCompletion(weeklyTaskCompletion)
                .rewardHistory(rewardHistory)
                .build();
    }
}
