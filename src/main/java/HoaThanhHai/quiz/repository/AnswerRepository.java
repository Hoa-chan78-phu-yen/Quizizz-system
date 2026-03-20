package HoaThanhHai.quiz.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import HoaThanhHai.quiz.entity.Answer;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    List<Answer> findByQuestion_QuestionId(Integer questionId);

    Optional<Answer> findByQuestion_QuestionIdAndIsCorrectTrue(Integer questionId);
}