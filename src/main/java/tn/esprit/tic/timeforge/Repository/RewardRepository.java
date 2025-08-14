package tn.esprit.tic.timeforge.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.timeforge.Entity.Reward;
import tn.esprit.tic.timeforge.dto.UserProductivity;
import tn.esprit.tic.timeforge.dto.UserRewardCountDTO;

import java.util.List;

@Repository
public interface RewardRepository extends JpaRepository<Reward ,Long> {



        @Query("SELECT r.employee.name AS name, COUNT(r) AS rewardCount " +
                "FROM Reward r " +
                "GROUP BY r.employee.name " +
                "ORDER BY COUNT(r) DESC")
        List<UserRewardCountDTO> findTopRewardedUsers(org.springframework.data.domain.Pageable pageable);

        int countByEmployee_IdUser(Long userId);

        List<Reward> findByEmployee_IdUser(Long idUser);




}
