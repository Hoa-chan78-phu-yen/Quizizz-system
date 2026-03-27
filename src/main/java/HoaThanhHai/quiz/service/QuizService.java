package HoaThanhHai.quiz.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import HoaThanhHai.quiz.dto.request.CreateQuestionDTO;
import HoaThanhHai.quiz.entity.Answer;
import HoaThanhHai.quiz.entity.Category;
import HoaThanhHai.quiz.entity.Question;
import HoaThanhHai.quiz.entity.Quiz;
import HoaThanhHai.quiz.repository.AnswerRepository;
import HoaThanhHai.quiz.repository.CategoryRepository;
import HoaThanhHai.quiz.repository.QuestionRepository;
import HoaThanhHai.quiz.repository.QuizRepository;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private QuestionRepository questionRepository;
    
    @Autowired
    private AnswerRepository answerRepository;
    
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
    
    public Quiz createQuizWithQuestions(String title, String description, Integer time, 
                                        Integer categoryId, List<CreateQuestionDTO> questionDTOs) {
        // Tạo quiz
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setDescription(description);
        quiz.setTotalTimeLimit(time);
        quiz.setCategory(category);
        quiz = quizRepository.save(quiz);

        // Tạo questions và answers
        List<Question> questions = new ArrayList<>();
        
        for (CreateQuestionDTO questionDTO : questionDTOs) {
            Question question = new Question();
            question.setContent(questionDTO.getContent());
            question.setCategory(category);
            question = questionRepository.save(question);

            // Tạo answers
            List<Answer> answers = new ArrayList<>();
            for (int i = 0; i < questionDTO.getAnswers().size(); i++) {
                Answer answer = new Answer();
                answer.setContent(questionDTO.getAnswers().get(i).getAnswerText());
                answer.setIsCorrect(questionDTO.getAnswers().get(i).getIsCorrect());
                answer.setQuestion(question);
                answer = answerRepository.save(answer);
                answers.add(answer);
            }
            question.setAnswers(answers);
            questions.add(question);
        }

        // Gán questions vào quiz
        quiz.setQuestions(questions);
        return quizRepository.save(quiz);
    }
}