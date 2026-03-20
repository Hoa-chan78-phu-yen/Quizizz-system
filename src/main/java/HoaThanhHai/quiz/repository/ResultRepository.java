package HoaThanhHai.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import HoaThanhHai.quiz.entity.Result;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Integer> {

    List<Result> findByUser_UserId(Integer userId);

    List<Result> findByQuiz_QuizId(Integer quizId);
}
