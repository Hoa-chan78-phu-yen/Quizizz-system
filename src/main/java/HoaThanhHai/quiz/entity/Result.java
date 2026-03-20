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
    @Column(name = "result_id")
    private Integer resultId;

    private Double score;

    @Column(name = "total_correct")
    private Integer totalCorrect;

    @Column(name = "total_questions")
    private Integer totalQuestions;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "actual_duration")
    private Integer actualDuration;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(mappedBy = "result")
    private List<UserAnswer> userAnswers;

}