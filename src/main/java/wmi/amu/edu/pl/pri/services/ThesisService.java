package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wmi.amu.edu.pl.pri.dto.ReviewDto;
import wmi.amu.edu.pl.pri.dto.ThesisCompleteDto;
import wmi.amu.edu.pl.pri.dto.ThesisCoreDto;
import wmi.amu.edu.pl.pri.models.ThesisModel;
import wmi.amu.edu.pl.pri.repositories.ThesisRepo;

import java.security.Key;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThesisService {

    @Value("${pri.app.jwtSecret}")
    private String jwtSecret;

    private final ThesisRepo thesisRepo;

    public ThesisCompleteDto getById(Long id) {

        return findById(id).map(ThesisModel::toCompleteDto).orElse(null);
    }

    public Optional<ThesisModel> findById(Long id) {
        return thesisRepo.findById(id);
    }

    public ThesisCompleteDto findByProjectId(Long id) {

        return thesisRepo.findByProjectId(id).map(ThesisModel::toCompleteDto).orElse(null);
    }

    public ThesisCompleteDto update(Long id, ThesisCoreDto dto) {

        Optional<ThesisModel> model = thesisRepo.findById(id);
        if (model.isEmpty())
            return null;
        else {
            if (isApproved(model))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt to modify APPROVED thesis with id: %d, which is not allowed.".formatted(id));
            else {
                model.ifPresent(m -> m.applyDataFrom(dto));
                return thesisRepo.save(model.get()).toCompleteDto();
            }
        }
    }

    private boolean isApproved(Optional<ThesisModel> model) {
        return model.get().getApprovalStatus().equals("APPROVED");
    }

    public ThesisCompleteDto confirm(Long id) {
        ThesisModel thesis = thesisRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Thesis not found with ID: " + id));
        thesis.setApprovalStatus("APPROVED");
        return thesisRepo.save(thesis).toCompleteDto();
    }

    public ReviewDto findByIdAuthorizeAndGetReview(Long thesisId, String cookieHeader) {
        // Extract accessToken from Cookie header
        String accessToken = extractAccessTokenFromCookie(cookieHeader);
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing accessToken in Cookie header");
        }

        // Parse JWT and extract 'sub' claim
        String subject = extractSubjectFromJwt(accessToken);


        // Retrieve thesis by ID
        ThesisModel thesis = thesisRepo.findById(thesisId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Thesis not found with ID: " + thesisId));

        if (!thesis.getProject().getSupervisor().getUserData().getIndexNumber().equals(subject)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied to thesis review");
        }

        // Return the ReviewDto with thesisId and review
        return new ReviewDto(thesis.getId(), thesis.getReview());
    }

    public ReviewDto findByIdAuthorizeAndSetReview(Long thesisId, String cookieHeader, String reviewContent) {
        String accessToken = extractAccessTokenFromCookie(cookieHeader);
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing accessToken in Cookie header");
        }

        String subject = extractSubjectFromJwt(accessToken);

        // Retrieve thesis by ID
        ThesisModel thesis = thesisRepo.findById(thesisId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Thesis not found with ID: " + thesisId));

        if (!thesis.getProject().getSupervisor().getUserData().getIndexNumber().equals(subject)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied to thesis review");
        }

        thesis.setReview(reviewContent);

        ThesisModel updatedThesis = thesisRepo.save(thesis);

        return new ReviewDto(updatedThesis.getId(), updatedThesis.getReview());
    }

    private String extractAccessTokenFromCookie(String cookieHeader) {
        if (cookieHeader == null || cookieHeader.isEmpty()) {
            return null;
        }

        for (String cookie : cookieHeader.split(";")) {
            String trimmedCookie = cookie.trim();
            if (trimmedCookie.startsWith("accessToken=")) {
                return trimmedCookie.substring("accessToken=".length());
            }
        }
        return null;
    }

    private String extractSubjectFromJwt(String jwt) {
        try {
            return io.jsonwebtoken.Jwts.parserBuilder()
                    .setSigningKey(key()) // Replace key() with your method to retrieve the signing key
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();
        } catch (io.jsonwebtoken.JwtException e) {
            throw new IllegalArgumentException("Invalid JWT format", e);
        }
    }

    private Key key() {

        return io.jsonwebtoken.security.Keys.hmacShaKeyFor(
                java.util.Base64.getDecoder().decode(jwtSecret)
        );
    }
}
