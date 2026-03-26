package HoaThanhHai.quiz.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resultId;

    private Double score;

    private Integer totalCorrect;

    private Integer totalQuestions;

    private LocalDateTime completedAt;

    private Integer actualDuration;

    @ManyToOne(optional = false)

    private User user;

    @ManyToOne
    private Quiz quiz;

    @OneToMany(mappedBy = "result")
    private List<UserAnswer> userAnswers;

}