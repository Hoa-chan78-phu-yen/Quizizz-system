package HoaThanhHai.quiz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import HoaThanhHai.quiz.entity.Answer;
import HoaThanhHai.quiz.repository.AnswerRepository;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    public List<Answer> getByQuestion(Integer questionId) {
        return answerRepository.findByQuestion_QuestionId(questionId);
    }
}
