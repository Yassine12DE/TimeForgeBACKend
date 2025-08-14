package tn.esprit.tic.timeforge.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long projectid;
    String name;
    String description;
    Date deadline;
    File cahiercharge;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy="project")
    private Set<ProjectComment> ProjectComments;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy="project")
    private Set<ProjectProgress> ProjectProgresss;
    @JsonIgnore
    @OneToOne(mappedBy = "project")
    private Deadline deadlinee;

    @OneToMany(cascade = CascadeType.ALL,fetch =FetchType.LAZY ,mappedBy="project")
    @JsonIgnoreProperties("project")
    private Set<Task> Taskss;
    @JsonIgnore
    @ManyToOne
    User teamlead;
    @JsonIgnore
    @ManyToOne
    ProductivityDshbord productivitydshbord;
}
