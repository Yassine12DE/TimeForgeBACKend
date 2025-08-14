package tn.esprit.tic.timeforge.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.tic.timeforge.Entity.Ennum.Status;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification   {
    @Id
    @GeneratedValue
    int id;
    String title;
    String message;
    String date;
    Status status;


    @ManyToOne
    @JsonIgnore
    HealthReminder HealthReminder;
    @ManyToOne
    @JsonIgnore
    StrategicReminder strategicreminder;
    @ManyToOne
    @JsonIgnore
    Interface interface1;
    @ManyToOne
    @JsonIgnore
    Deadline deadline;



}
