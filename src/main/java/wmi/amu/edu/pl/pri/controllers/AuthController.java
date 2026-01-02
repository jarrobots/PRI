package wmi.amu.edu.pl.pri.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import wmi.amu.edu.pl.pri.dto.LoginRequestDto;
import wmi.amu.edu.pl.pri.dto.LoginResponseDto;
import wmi.amu.edu.pl.pri.security.JwtTokenProvider;
import wmi.amu.edu.pl.pri.services.AuthService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    public static final long VALIDITY_MILLIS = 24 * 60 * 60 * 1000; // 24h
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDto> getToken(@RequestBody LoginRequestDto request) {
        try {
            LoginResponseDto response = authService.authenticateAndCreateToken(request);

            ResponseCookie jwtCookie = generateTokenCookie(response.token());

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .header("Access-Control-Expose-Headers", "Set-Cookie")
                    .body(response);

        } catch (AuthenticationException ae) {
            log.info(ae.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private ResponseCookie generateTokenCookie(String token) {
        return ResponseCookie.from("access_token", token)
                .httpOnly(true)
//                .secure(false)
                .path("/")
                .maxAge(VALIDITY_MILLIS / 1000)
                // allow cross-site requests (frontend on different origin) to send the cookie
                .sameSite("None")
                .build();
    }

    @GetMapping("/auth/token")
    public String getToken(
            @RequestParam String username
    ) {
//        List<String> roleList = Arrays.asList(roles.split(","));
        return jwtTokenProvider.createToken(username, List.of("aaa"), VALIDITY_MILLIS);
    }
}
