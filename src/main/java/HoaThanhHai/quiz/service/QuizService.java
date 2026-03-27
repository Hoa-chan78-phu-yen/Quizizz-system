package HoaThanhHai.quiz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import HoaThanhHai.quiz.entity.Category;
import HoaThanhHai.quiz.entity.Quiz;
import HoaThanhHai.quiz.repository.CategoryRepository;
import HoaThanhHai.quiz.repository.QuizRepository;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    
    public List<Quiz> getAllQuiz() {
        return quizRepository.findAll();
    }

    public Quiz getQuizById(Integer id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
    }
    
    public Quiz createQuiz(Quiz quiz) {
    return quizRepository.save(quiz);
    }
    
    public Quiz createQuiz(String title, String description, Integer time, Integer categoryId) {

    Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Category not found"));

    Quiz quiz = new Quiz();
    quiz.setTitle(title);
    quiz.setDescription(description);
    quiz.setTotalTimeLimit(time);
    quiz.setCategory(category);

    return quizRepository.save(quiz);
}
}