package tn.esprit.tic.timeforge.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectComment   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    Long projectId;
    Long userId;
    String comment;
    Date createdAt;
    @ManyToOne
    @JsonIgnore
    Project project;
}
