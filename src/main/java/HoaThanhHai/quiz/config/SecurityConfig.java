package HoaThanhHai.quiz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import HoaThanhHai.quiz.security.JwtFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/",
                "/index",
                "/login",
                "/register",
                "/join",
                "/activity",
                "/css/**",
                "/js/**",
                "/images/**"
            ).permitAll()
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/api/admin/**").hasRole("Admin")
            .requestMatchers("/api/quiz/**").hasAnyRole("User","Admin")
            .requestMatchers("/quiz", "/quiz/**").hasAnyRole("User","Admin")
            .requestMatchers("/result/**").hasAnyRole("User","Admin")
            .requestMatchers("/history").hasAnyRole("User","Admin")
            .requestMatchers("/quiz/create").hasAnyRole("User","Admin")
            .anyRequest()
            .authenticated())
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/quiz", true)
            .permitAll());
        return http.build();
    }  
}
