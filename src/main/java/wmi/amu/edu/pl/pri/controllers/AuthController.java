package wmi.amu.edu.pl.pri.controllers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.web.bind.annotation.*;
import wmi.amu.edu.pl.pri.security.JwtTokenProvider;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    // 24h
    public static final long VALIDITY_MILLIS = 24 * 60 * 60 * 1000;
    private final JwtTokenProvider jwtTokenProvider;
    private final ActiveDirectoryLdapAuthenticationProvider ldapProvider;

//    @PostMapping("/auth/login")
//    public ResponseEntity<TokenResponse> getToken(@RequestBody LoginRequest request) {
//        String username = request.getUsername();
//
//        try {
//            UsernamePasswordAuthenticationToken authToken =
//                    new UsernamePasswordAuthenticationToken(username, request.getPassword());
//
//            Authentication auth = ldapProvider.authenticate(authToken);
//
//            // extract roles from authenticated principal
//            List<String> roles = auth.getAuthorities().stream()
//                    .map(GrantedAuthority::getAuthority)
//                    .collect(Collectors.toList());
//
//            String token = jwtTokenProvider.createToken(username, roles.isEmpty() ? Collections.singletonList("USER") : roles, VALIDITY_MILLIS);
//
//            ResponseCookie jwtCookie = generateTokenCookie(token);
//
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
//                    .body(new TokenResponse(token));
//
//        } catch (AuthenticationException ae) {
//            log.info(ae.getMessage());
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @PostMapping("/auth/login")
    public ResponseEntity<TokenResponse> getTokenBypassLDAP(@RequestBody LoginRequest request) {
        String token = jwtTokenProvider.createToken(request.username, List.of("USER"), VALIDITY_MILLIS);
        var jwtCookie = generateTokenCookie(token);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new TokenResponse(token));
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
            @RequestParam String username,
            @RequestParam String roles
    ) {
        List<String> roleList = Arrays.asList(roles.split(","));
        return jwtTokenProvider.createToken(username, roleList, VALIDITY_MILLIS);
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
