package HoaThanhHai.quiz.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import HoaThanhHai.quiz.entity.Quiz;
import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    List<Quiz> findByCategory_CategoryId(Integer categoryId);
}