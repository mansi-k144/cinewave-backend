package reactjavaproject.cinewave.config;

import java.io.IOException;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
protected void doFilterInternal(HttpServletRequest request, 
                              HttpServletResponse response, 
                              FilterChain filterChain) 
    throws ServletException, IOException {

    String path = request.getRequestURI();

    // ⛔ Skip JWT validation for public endpoints
    if (path.startsWith("/api/auth") || path.startsWith("/api/media")) {
        filterChain.doFilter(request, response);
        return;
    }

    try {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtUtil.validateToken(token)) {
                String email = jwtUtil.extractEmail(token);
                var authentication = new UsernamePasswordAuthenticationToken(
                    email, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid token");
                return;
            }
        } else {
            // No token = not authenticated for protected routes
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Missing token");
            return;
        }

        filterChain.doFilter(request, response);

    } catch (Exception e) {
        response.sendError(HttpStatus.UNAUTHORIZED.value(), "Authentication failed");
    }
}

}