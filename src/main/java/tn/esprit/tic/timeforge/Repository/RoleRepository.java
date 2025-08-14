package tn.esprit.tic.timeforge.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.timeforge.Entity.Ennum.RoleName;
import tn.esprit.tic.timeforge.Entity.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
