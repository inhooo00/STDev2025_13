package shop.itcontest17.stdev2025_13.auth.api.dto.response;

public record IdTokenAndAccessTokenResponse(
        String access_token,
        String id_token
) {}

