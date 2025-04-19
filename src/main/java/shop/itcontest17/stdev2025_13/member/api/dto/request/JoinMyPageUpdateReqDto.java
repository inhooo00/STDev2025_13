package shop.itcontest17.stdev2025_13.member.api.dto.request;

public record JoinMyPageUpdateReqDto(
        String introduction,
        String nationality,
        String school,
        String nickname
) {
}