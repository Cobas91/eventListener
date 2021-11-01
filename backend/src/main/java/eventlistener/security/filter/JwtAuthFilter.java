package eventlistener.security.filter;

import eventlistener.security.service.JWTUtilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JWTUtilService jwtUtil;

    public JwtAuthFilter(JWTUtilService jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getAuthToken(request);
        try{
            if(token != null && !token.isBlank()){
                String username = jwtUtil.extractUsername(token);
                setSecurityContext(username);
            }
        }catch (Exception e){
            log.error("No valid Token found!", e);
        }
        filterChain.doFilter(request, response);
    }

    private void setSecurityContext(String username) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, "", List.of());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private String getAuthToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null){
            return authHeader.replace("Bearer", "").trim();
        }
        return null;
    }
}
