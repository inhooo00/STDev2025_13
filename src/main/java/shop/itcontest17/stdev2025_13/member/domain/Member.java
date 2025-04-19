package shop.itcontest17.stdev2025_13.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itcontest17.stdev2025_13.global.entity.BaseEntity;
import shop.itcontest17.stdev2025_13.global.entity.Status;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Status status;

    private String email;

    private String name;

    private String nickname;

    private String picture;

    @Enumerated(value = EnumType.STRING)
    private SocialType socialType;

    private String introduction = "";

    @Builder
    private Member(Status status,
                   String email, String name,
                   String picture,
                   SocialType socialType,
                   String introduction
    ) {
        this.status = status;
        this.email = email;
        this.name = name;
        this.picture = picture;
        this.socialType = socialType;
        this.introduction = introduction;
        this.nickname = email.split("@")[0];
    }
}