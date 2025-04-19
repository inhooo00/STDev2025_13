package shop.itcontest17.stdev2025_13.member.api.dto.response;

import lombok.Builder;
import shop.itcontest17.stdev2025_13.process.domain.Processes;

@Builder
public record ProcessDetail(
        String emotion,
        String question,
        String answer,
        String firstResult,
        String image,
        String summary
) {
    public static ProcessDetail fromEntity(Processes processes) {
        return new ProcessDetail(
                processes.getEmotion(),
                processes.getQuestion(),
                processes.getAnswer(),
                processes.getFirstResult(),
                processes.getImage(),
                processes.getSummary()
        );
    }
}
