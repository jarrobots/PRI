package wmi.amu.edu.pl.pri.controllers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wmi.amu.edu.pl.pri.security.JwtTokenProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/auth/login")
    public ResponseEntity<TokenResponse> getToken(@RequestBody LoginRequest request) {
        String username = request.getUsername();
        long validityMillis = 24 * 60 * 60 * 1000; // 24h
        String token = jwtTokenProvider.createToken(username, Collections.singletonList("USER"), validityMillis);

        ResponseCookie jwtCookie = ResponseCookie.from("access_token", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(validityMillis / 1000)
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new TokenResponse(token));
    }

    @GetMapping("/auth/token")
    public String getToken(
            @RequestParam String username,
            @RequestParam String roles
    ) {
        List<String> roleList = Arrays.asList(roles.split(","));
        long validityMillis = 24 * 60 * 60 * 1000; // 24h
        return jwtTokenProvider.createToken(username, roleList, validityMillis);
    }


    @Data
    public static class LoginRequest {
        private String username;
        private String password; // @todo integracja z LDAPem
    }

    @Data
    public static class TokenResponse {
        private final String token;
    }
}
