package HoaThanhHai.quiz.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import HoaThanhHai.quiz.entity.Quiz;
import HoaThanhHai.quiz.entity.Result;
import HoaThanhHai.quiz.service.QuizService;
import HoaThanhHai.quiz.service.ResultService;

import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private ResultService resultService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/quizzes")
    public String quizzes(Model model) {
        List<Quiz> quizzes = quizService.getAllQuiz();
        model.addAttribute("quizzes", quizzes);
        return "quiz-list";
    }

    @GetMapping("/quiz/{id}")
    public String quiz(@PathVariable Integer id, Model model) {

        Quiz quiz = quizService.getQuizById(id);

        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", quiz.getQuestions());

        return "take-quiz";
    }

    @GetMapping("/result/{id}")
    public String result(@PathVariable Integer id, Model model) {

        Result result = resultService.getById(id);

        model.addAttribute("result", result);

        return "quiz-result";
    }

    @GetMapping("/history")
    public String history(Model model, Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        List<Result> results = resultService.getByUser(principal.getName());

        model.addAttribute("results", results);

        return "quiz-history";
    }
    @GetMapping("/quiz/start/{id}")
    public String startQuizPage(@PathVariable("id") Long id) {
        // Trong thực tế, bạn sẽ dùng QuizService để tìm bài thi theo ID
        // và đẩy dữ liệu câu hỏi xuống Model trước khi return.
        // Hiện tại chỉ trả về giao diện tĩnh:
        return "quiz-taking";
    }
    @GetMapping("/quiz/result")
    public String resultPage() {
        return "quiz-result";
    }
    @GetMapping("/join")
    public String joinRoomPage() {
        return "join-room";
    }
}

