package tn.esprit.tic.timeforge.Repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.tic.timeforge.Entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findAllByChannel(String channel, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update message set read_date = now() "
            + " where channel = ?1 and sender = ?2 and read_date is null", nativeQuery = true)
    void sendReadReceipt(String channel, String username);
}