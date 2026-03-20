package HoaThanhHai.quiz.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import HoaThanhHai.quiz.entity.UserAnswer;

import java.util.List;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Integer> {

    List<UserAnswer> findByResult_ResultId(Integer resultId);
}