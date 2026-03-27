package HoaThanhHai.quiz.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuestionDTO {
    private String content;
    private List<CreateAnswerDTO> answers;
    private Integer correctAnswer; // index of correct answer (0-3)
}
