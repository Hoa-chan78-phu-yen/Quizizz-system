package HoaThanhHai.quiz.security;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response,
                                    FilterChain filterChain) 
                                    throws ServletException, IOException{
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);

            if (jwtUtil.validateToken(token)) {
                String username =jwtUtil.extractUserName(token);
                String role = jwtUtil.extractRole(token);
                
                System.out.println("USERNAME: " + username);
                System.out.println("ROLE: " + role);

                List<SimpleGrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_" + role));

                UsernamePasswordAuthenticationToken auth = 
                new UsernamePasswordAuthenticationToken(
                    username,null, authorities);

            SecurityContextHolder.getContext().setAuthentication(auth);
                
            }
        }
        filterChain.doFilter(request, response);
    }
    
}
