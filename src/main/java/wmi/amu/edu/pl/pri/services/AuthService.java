package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wmi.amu.edu.pl.pri.dto.LoginRequestDto;
import wmi.amu.edu.pl.pri.dto.LoginResponseDto;
import wmi.amu.edu.pl.pri.models.pri.RoleModel;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;
import wmi.amu.edu.pl.pri.repositories.UserDataRepo;
import wmi.amu.edu.pl.pri.security.JwtTokenProvider;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    // 24h
    public static final long VALIDITY_MILLIS = 24 * 60 * 60 * 1000;

    private final JwtTokenProvider jwtTokenProvider;
    private final ActiveDirectoryLdapAuthenticationProvider ldapProvider;
    private final UserDataRepo userDataRepo;

    @Value("${pri.ldap.enabled}")
    private boolean ldapEnabled;

    public LoginResponseDto authenticateAndCreateToken(LoginRequestDto request) throws AuthenticationException {
        String login = request.username();

        Authentication auth = null;

        if (ldapEnabled) {
            // when LDAP is enabled, perform LDAP authentication first
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(login, request.password());

            auth = ldapProvider.authenticate(authToken);
        } else {
            log.info("LDAP authenticatiojn is disabled (pri.ldap.enabled=false) — skipping LDAP authentication for login='{}'", login);
        }

        // Ensure we have user_data in the app DB regardless of LDAP being enabled/disabled
        Optional<UserDataModel> userData = getUserDataFromDatabase(login);

        // roles from LDAP are ignored, only those in db matters in context of this module
        List<String> roles = userData.get().getRoles().stream()
                .map(RoleModel::getName)
                .collect(Collectors.toList());


        String token = jwtTokenProvider.createToken(login, roles, VALIDITY_MILLIS);

        UserDataModel u = userData.get();
        Long userId = u.getId();
        String firstName = u.getFirstName() == null ? "" : u.getFirstName();
        String lastName = u.getLastName() == null ? "" : u.getLastName();

        // frontend  expects 'isPromoter' to denote supervisors
        boolean isPromoter = roles.stream().anyMatch(r -> r.equalsIgnoreCase("SUPERVISOR"));

        return new LoginResponseDto(token, userId, firstName, lastName, isPromoter, roles);
    }

    private Optional<UserDataModel> getUserDataFromDatabase(String login) {
        Optional<UserDataModel> userData = userDataRepo.findByIndexNumber(login);
        if (userData.isEmpty()) {
            log.info("No user_data entry found for login='{}'", login);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User data not found for login: " + login);
        }
        return userData;
    }
}
