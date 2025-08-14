package tn.esprit.tic.timeforge.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("Admin")


public class CompletedTasksHistory  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;





}
