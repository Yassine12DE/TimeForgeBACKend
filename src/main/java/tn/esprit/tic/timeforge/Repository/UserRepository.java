package tn.esprit.tic.timeforge.Repository;

import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.timeforge.Entity.Role;
import tn.esprit.tic.timeforge.Entity.User;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
        Set<User> findAllByRoles(Role role);
        Optional<User> findByUsername(String username);


    Optional<User> findByResetpasswordtoken(String token);
}
