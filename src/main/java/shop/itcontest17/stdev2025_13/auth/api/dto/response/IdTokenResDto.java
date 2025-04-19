package shop.itcontest17.stdev2025_13.auth.api.dto.response;

import com.fasterxml.jackson.databind.JsonNode;

public record IdTokenResDto(
        JsonNode idToken
) {
}
