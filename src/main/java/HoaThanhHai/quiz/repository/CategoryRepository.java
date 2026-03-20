package HoaThanhHai.quiz.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import HoaThanhHai.quiz.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
