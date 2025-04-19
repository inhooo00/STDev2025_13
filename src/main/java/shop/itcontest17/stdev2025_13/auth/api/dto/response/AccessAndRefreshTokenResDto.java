package shop.itcontest17.stdev2025_13.auth.api.dto.response;

import groovy.transform.builder.Builder;

@Builder
public record AccessAndRefreshTokenResDto(
        String accessToken,
        String refreshToken
) {
}
