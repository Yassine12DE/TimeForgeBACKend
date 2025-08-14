package tn.esprit.tic.timeforge.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tic.timeforge.Entity.Project;
import tn.esprit.tic.timeforge.Entity.User;

import java.util.List;

public interface IprojetRepo extends JpaRepository<Project,Long> {

    List<Project> findAllByTeamlead(User teamlead);
}
