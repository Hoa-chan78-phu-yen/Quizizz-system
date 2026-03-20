package HoaThanhHai.quiz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import HoaThanhHai.quiz.entity.Question;
import HoaThanhHai.quiz.repository.QuestionRepository;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getByCategory(Integer categoryId) {
        return questionRepository.findByCategory_CategoryId(categoryId);
    }
}