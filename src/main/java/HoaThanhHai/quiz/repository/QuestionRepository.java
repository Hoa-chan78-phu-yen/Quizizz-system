package HoaThanhHai.quiz.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import HoaThanhHai.quiz.entity.Question;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findByCategory_CategoryId(Integer categoryId);
}