package tn.esprit.tic.timeforge.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.timeforge.Entity.Goals;

import java.util.List;

@Repository
public interface GoalsRepository extends JpaRepository<Goals, Long> {
    List<Goals> findGoalsByTask13_Id(Long taskId);
    List<Goals> findByChronicActiveTrue();
}

