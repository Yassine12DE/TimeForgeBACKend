package tn.esprit.tic.timeforge.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Deadline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String details;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "project_id") // Le nom de la colonne dans la table deadline
    private Project project;



    @OneToMany(cascade = CascadeType.ALL, mappedBy="deadline")
    @JsonIgnore
    private Set<Notification> Notifications;
}
