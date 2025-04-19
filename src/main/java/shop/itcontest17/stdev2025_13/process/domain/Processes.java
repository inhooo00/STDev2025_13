package shop.itcontest17.stdev2025_13.process.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itcontest17.stdev2025_13.global.entity.BaseEntity;
import shop.itcontest17.stdev2025_13.global.entity.Status;
import shop.itcontest17.stdev2025_13.member.domain.Member;

@Entity
@Getter
@NoArgsConstructor
public class Processes extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Status status;

    private String emotion;

    private String question;

    private String answer;

    private String firstResult;

    private String image;

    private String summary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private Processes(Member member, Status status,
                      String emotion, String question,
                      String answer, String firstResult,
                      String image, String summary
    ) {
        this.member = member;
        this.status = status;
        this.emotion = emotion;
        this.question = question;
        this.answer = answer;
        this.firstResult = firstResult;
        this.image = image;
        this.summary = summary;
    }
}