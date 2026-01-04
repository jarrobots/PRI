package wmi.amu.edu.pl.pri.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wmi.amu.edu.pl.pri.dto.LoginRequestDto;
import wmi.amu.edu.pl.pri.dto.LoginResponseDto;
import wmi.amu.edu.pl.pri.security.JwtTokenProvider;
import wmi.amu.edu.pl.pri.services.AuthService;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${pri.app.jwtCookieName:access_token}")
    private String jwtCookieName;

    @Value("${pri.app.jwtExpirationMs:86400000}")
    private long jwtExpirationMs;

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDto> getToken(@RequestBody LoginRequestDto request) {
        LoginResponseDto response = authService.authenticateAndCreateToken(request);


        ResponseCookie jwtCookie = generateTokenCookie(jwtCookieName, response.token(), jwtExpirationMs);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header("Access-Control-Expose-Headers", "Set-Cookie")
                .body(response);
    }

    private ResponseCookie generateTokenCookie(String name, String token, long expiryMillis) {
        return ResponseCookie.from(name, token)
                .httpOnly(true)
                .path("/")
                .maxAge(expiryMillis / 1000)
                .sameSite("None")
                .build();
    }
}
