package HoaThanhHai.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import HoaThanhHai.quiz.dto.request.LoginRequest;
import HoaThanhHai.quiz.dto.request.RegisterRequest;
import HoaThanhHai.quiz.dto.response.LoginResponse;
import HoaThanhHai.quiz.entity.User;
import HoaThanhHai.quiz.security.JwtUtil;
import HoaThanhHai.quiz.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request){
        return authService.register(request);
    }
    
    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse response) {
        LoginResponse loginResponse = authService.login(request);

        String username = jwtUtil.extractUserName(loginResponse.getToken());
        String role = jwtUtil.extractRole(loginResponse.getToken());

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + role)));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        new HttpSessionSecurityContextRepository().saveContext(context, httpRequest, response);
        
        // Set token to cookie
        Cookie tokenCookie = new Cookie("token", loginResponse.getToken());
        tokenCookie.setHttpOnly(false);  // Allow JavaScript access for localStorage
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(24 * 60 * 60);  // 24 hours
        response.addCookie(tokenCookie);
        
        return loginResponse;
    }
}
