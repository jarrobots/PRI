package wmi.amu.edu.pl.pri.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wmi.amu.edu.pl.pri.dto.LoginRequestDto;
import wmi.amu.edu.pl.pri.dto.LoginResponseDto;
import wmi.amu.edu.pl.pri.models.pri.RoleModel;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;
import wmi.amu.edu.pl.pri.repositories.UserDataRepo;
import wmi.amu.edu.pl.pri.security.JwtTokenProvider;
import wmi.amu.edu.pl.pri.security.UserPrincipal;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthService {
    // 24h
    public static final long VALIDITY_MILLIS = 24 * 60 * 60 * 1000;

    private final JwtTokenProvider jwtTokenProvider;
    private final ActiveDirectoryLdapAuthenticationProvider ldapProvider;
    private final UserDataRepo userDataRepo;
    private final boolean ldapEnabled;
    private final long jwtExpirationMs;

    public AuthService(JwtTokenProvider jwtTokenProvider,
                       ActiveDirectoryLdapAuthenticationProvider ldapProvider,
                       UserDataRepo userDataRepo,
                       @Value("${pri.ldap.enabled}") boolean ldapEnabled,
                       @Value("${pri.app.jwtExpirationMs}") long jwtExpirationMs) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.ldapProvider = ldapProvider;
        this.userDataRepo = userDataRepo;
        this.ldapEnabled = ldapEnabled;
        this.jwtExpirationMs = jwtExpirationMs;
    }

    public LoginResponseDto authenticateAndCreateToken(LoginRequestDto request) throws AuthenticationException {
        String login = request.username();

        if (ldapEnabled) {
            // when LDAP is enabled, perform LDAP authentication for side effects (exception on failure)
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(login, request.password());
            ldapProvider.authenticate(authToken);
        } else {
            log.info("LDAP authentication is disabled (pri.ldap.enabled=false) — skipping LDAP authentication for login='{}'", login);
        }

        // Ensure we have user_data in the app DB regardless of LDAP being enabled/disabled
        UserDataModel userData = getUserDataFromDatabase(login);

        // roles from LDAP are ignored, only those in db matters in context of this module
        List<String> roles = userData.getRoles().stream()
                .map(RoleModel::getName)
                .collect(Collectors.toList());

        // include userId claim in token (use pri.app.jwtExpirationMs configured in main app)
        Long userId = userData.getId();
        String token = jwtTokenProvider.createToken(login, roles, jwtExpirationMs, userId);

        // set principal with roles from DB into SecurityContext
        var authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserPrincipal principal = new UserPrincipal(userId, login, authorities);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, token, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String firstName = userData.getFirstName() == null ? "" : userData.getFirstName();
        String lastName = userData.getLastName() == null ? "" : userData.getLastName();

        // frontend expects 'isPromoter' to denote supervisors
        boolean isPromoter = roles.stream().anyMatch(r -> r.equalsIgnoreCase("SUPERVISOR"));

        return new LoginResponseDto(token, userId, firstName, lastName, isPromoter, roles);
    }

    private UserDataModel getUserDataFromDatabase(String login) {
        return userDataRepo.findByIndexNumber(login)
                .orElseThrow(() -> {
                    log.info("No user_data entry found for login='{}'", login);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User data not found for login: " + login);
                });
    }
}
