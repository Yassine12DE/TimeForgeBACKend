package tn.esprit.tic.timeforge.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reward  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate rewardDate ;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "rewardedemployee_id")
    private User employee;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "taskrewarded_id")
    private Task task;



}
