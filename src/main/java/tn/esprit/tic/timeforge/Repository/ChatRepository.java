package tn.esprit.tic.timeforge.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tic.timeforge.Entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}