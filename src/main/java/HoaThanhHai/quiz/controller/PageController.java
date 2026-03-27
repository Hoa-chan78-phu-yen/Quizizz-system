package HoaThanhHai.quiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/quizzes")
    public String quizzesPage() {
        return "quizzes";
    }
      @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
     @GetMapping("/index")
    public String indexPage() {
        return "index";
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
    @GetMapping("/activity")
    public String activityPage() {
        return "activity";
    }
}

