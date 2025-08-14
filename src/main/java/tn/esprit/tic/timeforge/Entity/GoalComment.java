package tn.esprit.tic.timeforge.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoalComment  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    int goalid;
    int userid;
    String comment;
    Date createdAt;
    @ManyToMany(mappedBy="goalcomments", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Task> tasks;
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Goals> goalss;
}
