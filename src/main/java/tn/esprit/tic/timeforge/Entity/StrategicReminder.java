package tn.esprit.tic.timeforge.Entity;

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
public class StrategicReminder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Title;
    private String Description;
    @ManyToOne
    private Goals goal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="strategicreminder")
    private Set<Notification> notifications;
    ;
}