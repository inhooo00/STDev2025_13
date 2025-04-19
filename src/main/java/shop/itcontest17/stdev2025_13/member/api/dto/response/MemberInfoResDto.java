package shop.itcontest17.stdev2025_13.member.api.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import shop.itcontest17.stdev2025_13.member.domain.Member;

@Builder
public record MemberInfoResDto(
        String picture,
        String email,
        String name,
        LocalDateTime createAt
) {
    public static MemberInfoResDto from(Member member) {
        return MemberInfoResDto.builder()
                .picture(member.getPicture())
                .email(member.getEmail())
                .name(member.getName())
                .createAt(member.getCreatedAt())
                .build();
    }
}
