package tn.esprit.tic.timeforge.Entity;

import tn.esprit.tic.timeforge.Entity.Ennum.StatusGoal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Goals implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Le titre ne peut pas être vide")
    @Size(max = 100,min=3,message = "Le titre ne doit pas dépasser 100 caractères")
    private String title;
    @NotBlank(message = "La description ne peut pas être vide")
    @Size(max = 500,min=3, message = "La description ne doit pas dépasser 500 caractères")
    private String description;
    //@FutureOrPresent(message = "La date de début doit être aujourd'hui ou dans le futur")
    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date starDate;
    //@Future(message = "La date de fin doit être dans le futur")
    private Date endDate;
    @Enumerated(EnumType.STRING)
    private StatusGoal statusGoal;
    private boolean chronicActive = false;
    @ManyToOne
    ProductivityDshbord productivitydshbord;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goal")
    @JsonIgnore
    private Set<StrategicReminder> strategicreminders;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goal")
    @JsonIgnore
    private Set<HealthReminder> healthReminders;

    @ManyToOne
    @JoinColumn(name = "user_id") // Nom de la colonne FK dans la table Goals
    @JsonIgnore
    private User user;
    @ManyToMany(mappedBy="goalss", cascade = CascadeType.ALL)
    private Set<GoalComment> goalcomments;
    @OneToMany(mappedBy = "goals")
    private List<SubGoal> subGoals;
    private boolean reminderSent = false;

    @ManyToOne
    @JsonIgnore
    Task task13;

}
