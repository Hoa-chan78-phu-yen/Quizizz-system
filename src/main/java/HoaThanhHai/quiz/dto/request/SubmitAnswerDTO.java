package HoaThanhHai.quiz.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitAnswerDTO {
    private Integer questionId;
    private Integer answerId;
}
