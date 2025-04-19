package shop.itcontest17.stdev2025_13.global.jwt.api.dto;

import lombok.Builder;

@Builder
public record TokenDto(
        String accessToken,
        String refreshToken
) {
}