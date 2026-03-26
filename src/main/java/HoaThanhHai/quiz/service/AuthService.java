package HoaThanhHai.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import HoaThanhHai.quiz.dto.request.LoginRequest;
import HoaThanhHai.quiz.dto.request.RegisterRequest;
import HoaThanhHai.quiz.dto.response.LoginResponse;
import HoaThanhHai.quiz.entity.User;
import HoaThanhHai.quiz.enums.Role;
import HoaThanhHai.quiz.repository.UserRepository;
import HoaThanhHai.quiz.security.JwtUtil;


@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User register(RegisterRequest request){

        if(userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("Username already exist");
        }

        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exist");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.User);

        return userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        System.out.println("Token:" + token);
        return new LoginResponse(token);
    }


}
