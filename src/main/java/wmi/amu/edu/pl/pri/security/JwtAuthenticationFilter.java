package wmi.amu.edu.pl.pri.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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
    private final String cookieName;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider,
                                   @Value("${security.jwt.header:Authorization}") String headerName,
                                   @Value("${security.jwt.prefix:Bearer }") String prefix,
                                   @Value("${security.jwt.cookie:access_token}") String cookieName) {
        this.tokenProvider = tokenProvider;
        this.headerName = headerName;
        this.prefix = prefix;
        this.cookieName = cookieName;
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
        // If no Authorization header, try to read token from configured cookie name
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (cookieName.equals(c.getName())) {
                        token = c.getValue();
                        break;
                    }
                }
            }
        }
        if (token != null && tokenProvider.validateToken(token)) {
            var auth = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}
