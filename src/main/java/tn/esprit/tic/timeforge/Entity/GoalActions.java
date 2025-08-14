package tn.esprit.tic.timeforge.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.tic.timeforge.Entity.Ennum.StatusGoal;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoalActions  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int goalid;
    String action;
    StatusGoal statusGoal;
    Date createdAt;
    @ManyToOne
    @JsonIgnore
    SubGoal subgoal;
}
