package HoaThanhHai.quiz.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionId;

    private String content;

    private Integer timeLimitPerQuestion;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User createdBy;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;
}