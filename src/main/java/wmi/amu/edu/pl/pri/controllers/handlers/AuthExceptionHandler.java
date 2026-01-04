package wmi.amu.edu.pl.pri.controllers.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.CommunicationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class AuthExceptionHandler {

    @ExceptionHandler(CommunicationException.class)
    public ResponseEntity<Map<String, String>> handleLdapCommunication(CommunicationException ex) {
        // log for diagnostics
        log.error("LDAP communication error: {}", ex.getMessage(), ex);
        // LDAP is unavailable
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .header("X-Auth-Error", "LDAP_UNAVAILABLE")
                .body(Map.of(
                        "error", "LDAP_UNAVAILABLE",
                        "message", "Brak połączenia z serwerem LDAP. Spróbuj ponownie później."
                ));
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<Map<String, String>> handleInternalAuthService(InternalAuthenticationServiceException ex) {
        log.error("Internal authentication service exception: {}", ex.getMessage(), ex);
        Throwable cause = ex.getCause();
        if (cause instanceof CommunicationException) {
            return handleLdapCommunication((CommunicationException) cause);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header("X-Auth-Error", "AUTH_INTERNAL_ERROR")
                .body(Map.of(
                        "error", "AUTH_INTERNAL_ERROR",
                        "message", "Wewnętrzny błąd uwierzytelniania. Skontaktuj się z administratorem."
                ));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthException(AuthenticationException ex) {
        log.info("Authentication failed: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "error", "UNAUTHORIZED",
                        "message", "Nieprawidłowe dane logowania"
                ));
    }
}
