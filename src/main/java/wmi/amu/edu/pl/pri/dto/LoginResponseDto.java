package wmi.amu.edu.pl.pri.dto;

import java.util.List;

public record LoginResponseDto(
        String token,
        Long id,
        String firstName,
        String lastName,
        boolean isPromoter,
        List<String> roles
) {
}