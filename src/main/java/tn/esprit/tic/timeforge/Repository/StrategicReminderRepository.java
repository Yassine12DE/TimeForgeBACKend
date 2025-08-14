package tn.esprit.tic.timeforge.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.timeforge.Entity.StrategicReminder;
@Repository
public interface StrategicReminderRepository extends JpaRepository<StrategicReminder, Long> {
}
