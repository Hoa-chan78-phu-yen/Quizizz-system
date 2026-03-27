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
public class CreateQuizRequest {
    private String title;
    private String description;
    private Integer totalTimeLimit;
    private Integer categoryId;
    private String categoryName;
    private List<CreateQuestionDTO> questions;
}
