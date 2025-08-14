package tn.esprit.tic.timeforge.Entity;


import tn.esprit.tic.timeforge.Entity.Ennum.TypeReminder;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HealthReminder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING) // Assure que l'énumération est stockée sous forme de chaîne
    private TypeReminder typeReminder;


//    @OneToMany(cascade = CascadeType.ALL, mappedBy="healthreminder")
//    private Set<Supervisor> Supervisorrrs;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "HealthReminder")
    private Set<Notification> Notifications;

    @ManyToOne
    private Goals goal;
}


