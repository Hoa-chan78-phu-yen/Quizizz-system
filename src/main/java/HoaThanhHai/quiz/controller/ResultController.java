package HoaThanhHai.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import HoaThanhHai.quiz.dto.SubmitAnswerDTO;
import HoaThanhHai.quiz.entity.Result;
import HoaThanhHai.quiz.service.ResultService;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @PostMapping("/submit")
    public Result submitQuiz(
            @RequestParam Integer userId,
            @RequestParam Integer quizId,
            @RequestBody List<SubmitAnswerDTO> answers
    ) {
        return resultService.submitQuiz(userId, quizId, answers);
    }
}
