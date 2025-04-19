package shop.itcontest17.stdev2025_13.imageai.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextToImageReqDto {
    private String inputs;
}
