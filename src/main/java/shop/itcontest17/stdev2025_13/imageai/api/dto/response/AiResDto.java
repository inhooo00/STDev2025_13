package shop.itcontest17.stdev2025_13.imageai.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AiResDto {

    @NotNull
    @Schema(description = "AI 답변", example = "이렇게 이렇게 하면 될거야~")
    private String answer;
}
