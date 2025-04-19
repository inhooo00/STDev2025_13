package shop.itcontest17.stdev2025_13.auth.api.dto.response;

public record UserInfo(
        String email,
        String name,
        String picture,
        String nickname
) {
}
