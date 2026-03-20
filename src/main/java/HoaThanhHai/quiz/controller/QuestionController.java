package HoaThanhHai.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import HoaThanhHai.quiz.entity.Question;
import HoaThanhHai.quiz.service.QuestionService;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/category/{categoryId}")
    public List<Question> getByCategory(@PathVariable Integer categoryId) {
        return questionService.getByCategory(categoryId);
    }
}
