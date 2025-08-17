package tn.esprit.tic.timeforge.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import tn.esprit.tic.timeforge.Entity.Ennum.StatusTask;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private StatusTask status=StatusTask.TODO;
    private int priority;
    private LocalDate startDate;
    private LocalDate deadline;
    private long durationInDays;
    private LocalDateTime updatedAt;
    private int estimatedHours;
    private int taskCredit = 0;

    @ManyToOne
    @JoinColumn(name = "employee11Id",nullable = true)
    User employee11;
    @ManyToOne
    @JsonIgnore
    @JoinColumn( name = "teamleaderId",nullable = true)
    User teamleader;
    @ManyToOne
    @JoinColumn( name = "projectId",nullable = true)
    @JsonIgnoreProperties("taskss")
    Project project;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(  name = "productivityDshbordId",nullable = true)
    ProductivityDshbord productivitydshbord;
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<GoalComment> goalcomments;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy="task")
    @JsonIgnore
    private Set<FocusAnalysis> FocusAnalysiss;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task13")
    List<Goals> goals;

    @OneToMany(mappedBy = "task")
    private List<Reward> rewards;

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        if (startDate != null && deadline != null) {
            this.durationInDays = ChronoUnit.DAYS.between(startDate, deadline);
        } else {
            this.durationInDays = 0;
        }
    }

    @PrePersist
    public void onCreate() {
        this.updatedAt = LocalDateTime.now();
        if (startDate != null && deadline != null) {
            this.durationInDays = ChronoUnit.DAYS.between(startDate, deadline);
        } else {
            this.durationInDays = 0;
        }
    }

}
