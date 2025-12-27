package wmi.amu.edu.pl.pri.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider tokenProvider;
    private final String headerName;
    private final String prefix;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider,
                                   @Value("${security.jwt.header:Authorization}") String headerName,
                                   @Value("${security.jwt.prefix:Bearer }") String prefix) {
        this.tokenProvider = tokenProvider;
        this.headerName = headerName;
        this.prefix = prefix;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(headerName);
        String token = null;
        if (header != null && header.startsWith(prefix)) {
            token = header.substring(prefix.length());
        }
        if (token != null && tokenProvider.validateToken(token)) {
            var auth = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}

