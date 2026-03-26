package HoaThanhHai.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import HoaThanhHai.quiz.dto.request.LoginRequest;
import HoaThanhHai.quiz.dto.request.RegisterRequest;
import HoaThanhHai.quiz.dto.response.LoginResponse;
import HoaThanhHai.quiz.entity.User;
import HoaThanhHai.quiz.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request){
        return authService.register(request);
    }
    
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        
        return authService.login(request);
    }
}
