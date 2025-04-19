package shop.itcontest17.stdev2025_13.auth.api.dto.response;

import lombok.Builder;
import shop.itcontest17.stdev2025_13.member.domain.Member;

@Builder
public record MemberLoginResDto(
        Member findMember
) {
    public static MemberLoginResDto from(Member member) {
        return MemberLoginResDto.builder()
                .findMember(member)
                .build();
    }
}
