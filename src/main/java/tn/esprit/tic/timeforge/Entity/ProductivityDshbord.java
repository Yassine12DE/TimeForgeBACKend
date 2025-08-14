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
public class ProductivityDshbord  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="productivitydshbord")  @JsonIgnore

    private Set<Goals> Goalss;



    @OneToMany(cascade = CascadeType.ALL, mappedBy="productivitydshbord")
    @JsonIgnore
    private Set<Project> Projects;

    @ManyToOne
    @JsonIgnore
    private Distraction distraction;


    @OneToMany(cascade = CascadeType.ALL, mappedBy="productivitydshbord")
    @JsonIgnore
    private Set<Task> Tasks;
}
