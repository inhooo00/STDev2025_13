package shop.itcontest17.stdev2025_13.imageai.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AiReqDto {

    @NotNull
    @Schema(description = "AI 질문", example = "너무 힘들어요")
    private String question;
}
