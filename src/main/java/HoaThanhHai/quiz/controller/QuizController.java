package HoaThanhHai.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import HoaThanhHai.quiz.entity.Quiz;
import HoaThanhHai.quiz.service.QuizService;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping
    public List<Quiz> getAll() {
        return quizService.getAllQuiz();
    }

    @GetMapping("/{id}")
    public Quiz getById(@PathVariable Integer id) {
        return quizService.getQuizById(id);
    }
    @PostMapping
    public Quiz createQuiz(@RequestBody Quiz quiz) {
    return quizService.createQuiz(quiz);
    }
    
    
}
