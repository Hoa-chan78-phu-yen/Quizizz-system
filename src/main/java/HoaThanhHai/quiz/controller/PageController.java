package HoaThanhHai.quiz.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import HoaThanhHai.quiz.dto.request.CreateQuizRequest;
import HoaThanhHai.quiz.dto.request.SubmitAnswerDTO;
import HoaThanhHai.quiz.entity.Category;
import HoaThanhHai.quiz.entity.Quiz;
import HoaThanhHai.quiz.entity.Result;
import HoaThanhHai.quiz.entity.User;
import HoaThanhHai.quiz.repository.CategoryRepository;
import HoaThanhHai.quiz.repository.UserRepository;
import HoaThanhHai.quiz.service.CategoryService;
import HoaThanhHai.quiz.service.QuizService;
import HoaThanhHai.quiz.service.ResultService;

@Controller
public class PageController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private ResultService resultService;

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    @GetMapping("/")
    public String getStarted() {
        return "join-room";
    }
    @GetMapping("/index")
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

    @GetMapping("/quiz")
    public String quiz(Model model, Principal principal) {
        List<Quiz> quizzes = quizService.getAllQuiz();
        model.addAttribute("quiz", quizzes);
        return "quiz-list";
    }

    @GetMapping("/quiz/{id}")
    public String quiz(@PathVariable Integer id, Model model, Principal principal) {
        Quiz quiz = quizService.getQuizById(id);
        
        // Validation: Quiz must exist
        if (quiz == null) {
            return "redirect:/quiz?error=not-found";
        }

        model.addAttribute("quiz", quiz);
        model.addAttribute("quizId", id);
        model.addAttribute("quizTitle", quiz.getTitle());
        model.addAttribute("questions", quiz.getQuestions());
        model.addAttribute("timeLimit", quiz.getTotalTimeLimit());

        return "take-quiz";
    }

    @GetMapping("/result/{id}")
    public String result(@PathVariable Integer id, Model model) {

        Result result = resultService.getById(id);

        model.addAttribute("result", result);

        return "quiz-result";
    }
    
    @PostMapping("/quiz/{id}/submit")
    public String submitQuiz(
            @PathVariable Integer id,
            @RequestParam Map<String, String> params,
            Principal principal,
            Model model) {
        
        try {
            // Get user from Principal (guaranteed by Spring Security)
            User user = userRepository.findByUsername(principal.getName()).orElse(null);
            if (user == null) {
                return "redirect:/login?error=user-not-found";
            }
            
            // Parse form data: answers[questionId] = answerId
            List<SubmitAnswerDTO> answers = new ArrayList<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                
                // Extract questionId from "answers[questionId]" format
                if (key.startsWith("answers[") && key.endsWith("]")) {
                    try {
                        String questionIdStr = key.substring(8, key.length() - 1);
                        Integer questionId = Integer.parseInt(questionIdStr);
                        Integer answerId = Integer.parseInt(value);
                        
                        SubmitAnswerDTO dto = new SubmitAnswerDTO();
                        dto.setQuestionId(questionId);
                        dto.setAnswerId(answerId);
                        answers.add(dto);
                    } catch (NumberFormatException e) {
                        // Skip invalid entries
                        continue;
                    }
                }
            }
            
            // Validation: At least one answer must be provided
            if (answers.isEmpty()) {
                model.addAttribute("error", "Vui lòng trả lời ít nhất một câu hỏi");
                return "redirect:/quiz/" + id + "?error=no-answers";
            }
            
            // Submit quiz and get result
            Result result = resultService.submitQuiz(user.getUserId(), id, answers);
            
            // Redirect to result page
            return "redirect:/result/" + result.getResultId();
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/quiz/" + id + "?error=submission-failed";
        }
    }

    @GetMapping("/history")
    public String history(Model model, Principal principal) {
        List<Result> results = resultService.getByUser(principal.getName());
        model.addAttribute("results", results);
        return "quiz-history";
    }
    @GetMapping("/quiz/create")
    public String createQuizPage(Model model, Principal principal) {
        List<Category> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        return "create-quiz";
    }
    @PostMapping("/quiz/create")
    @ResponseBody
    public String createQuiz(@RequestBody CreateQuizRequest request, Principal principal) {
        try {
            // Xác định categoryId
            Integer finalCategoryId = request.getCategoryId();
            if (request.getCategoryName() != null && !request.getCategoryName().trim().isEmpty()) {
                // Tạo category mới nếu chưa tồn tại
                Category existingCategory = categoryRepository.findByCategoryName(request.getCategoryName()).orElse(null);
                if (existingCategory != null) {
                    finalCategoryId = existingCategory.getCategoryId();
                } else {
                    Category newCategory = new Category();
                    newCategory.setCategoryName(request.getCategoryName());
                    Category savedCategory = categoryRepository.save(newCategory);
                    finalCategoryId = savedCategory.getCategoryId();
                }
            }

            if (finalCategoryId == null) {
                throw new RuntimeException("Category không hợp lệ");
            }

            // Gọi service để tạo quiz với questions
            quizService.createQuizWithQuestions(
                request.getTitle(),
                request.getDescription() != null ? request.getDescription() : "",
                request.getTotalTimeLimit() != null ? request.getTotalTimeLimit() : 15,
                finalCategoryId,
                request.getQuestions()
            );

            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
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

