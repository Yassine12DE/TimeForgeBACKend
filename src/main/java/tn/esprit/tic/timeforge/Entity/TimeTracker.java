package tn.esprit.tic.timeforge.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeTracker  {
    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne
    Task task;
}
