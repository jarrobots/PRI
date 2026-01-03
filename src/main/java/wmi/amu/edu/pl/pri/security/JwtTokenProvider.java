package wmi.amu.edu.pl.pri.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    // use the same secret property as main module to ensure compatibility
    @Value("${pri.app.jwtSecret}")
    private String jwtSecret;

    // optional prefix (not used by main module) - keep for compatibility
    @Value("${security.jwt.prefix:Bearer }")
    private String tokenPrefix;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String username = claims.getSubject();
        List<String> roles = Optional.ofNullable(claims.get("roles", List.class)).orElse(Collections.emptyList());
        // read userId claim if present
        Long userId = null;
        Object userIdObj = claims.get("userId");
        if (userIdObj instanceof Number) {
            userId = ((Number) userIdObj).longValue();
        } else if (userIdObj instanceof String) {
            try { userId = Long.parseLong((String) userIdObj); } catch (NumberFormatException ignored) {}
        }
        UserPrincipal principal = new UserPrincipal(userId, username,
                roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }

    public String resolveToken(String header) {
        if (header == null || !header.startsWith(tokenPrefix)) return null;
        return header.substring(tokenPrefix.length());
    }

    // existing createToken kept for backward compatibility (without userId)
    public String createToken(String username, List<String> roles, long validityMillis) {
        return createToken(username, roles, validityMillis, null);
    }

    // new overload: include userId as claim
    public String createToken(String username, List<String> roles, long validityMillis, Long userId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityMillis);
        JwtBuilder builder = Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256);
        if (userId != null) {
            builder.claim("userId", userId);
        }
        return builder.compact();
    }
}
