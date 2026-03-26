package HoaThanhHai.quiz.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import HoaThanhHai.quiz.dto.request.SubmitAnswerDTO;
import HoaThanhHai.quiz.entity.Answer;
import HoaThanhHai.quiz.entity.Question;
import HoaThanhHai.quiz.entity.Quiz;
import HoaThanhHai.quiz.entity.Result;
import HoaThanhHai.quiz.entity.User;
import HoaThanhHai.quiz.entity.UserAnswer;
import HoaThanhHai.quiz.repository.AnswerRepository;
import HoaThanhHai.quiz.repository.QuizRepository;
import HoaThanhHai.quiz.repository.ResultRepository;
import HoaThanhHai.quiz.repository.UserAnswerRepository;
import HoaThanhHai.quiz.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ResultService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Transactional
    public Result submitQuiz(Integer userId, Integer quizId, List<SubmitAnswerDTO> answers) {

        User user = userRepository.findById(userId).orElseThrow();
        Quiz quiz = quizRepository.findById(quizId).orElseThrow();

        int score = 0;

        Result result = new Result();
        result.setUser(user);
        result.setQuiz(quiz);
        result.setTotalQuestions(answers.size());

        result = resultRepository.save(result);

        for (SubmitAnswerDTO dto : answers) {

            Answer correct = answerRepository
                    .findByQuestion_QuestionIdAndIsCorrectTrue(dto.getQuestionId())
                    .orElse(null);

            if (correct != null && correct.getAnswerId().equals(dto.getAnswerId())) {
                score++;
            }

            // Lưu user answer
            UserAnswer ua = new UserAnswer();
            ua.setResult(result);

            Question q = new Question();
            q.setQuestionId(dto.getQuestionId());
            ua.setQuestion(q);

            Answer a = new Answer();
            a.setAnswerId(dto.getAnswerId());
            ua.setAnswer(a);

            userAnswerRepository.save(ua);
        }

        result.setScore((double) score);
        result.setTotalCorrect(score);

        return resultRepository.save(result);
    }
}
