package tn.esprit.tic.timeforge.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Interface  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String theme;
    String alertSound;
    String layoutPreferences;




    @ManyToOne
    @JsonIgnore
    Deadline deadline;


    @OneToMany(cascade = CascadeType.ALL, mappedBy="interface1")
    @JsonIgnore
    private Set<Notification> Notifications;






}
