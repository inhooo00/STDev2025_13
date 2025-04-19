package shop.itcontest17.stdev2025_13.imageai.api;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itcontest17.stdev2025_13.global.template.RspTemplate;
import shop.itcontest17.stdev2025_13.imageai.api.dto.request.ImagePromptReqDto;
import shop.itcontest17.stdev2025_13.imageai.api.dto.response.ImageResDto;
import shop.itcontest17.stdev2025_13.imageai.application.HuggingFaceImageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stable-image")
public class HuggingFaceImageController implements HuggingFaceImageDocs{

    private final HuggingFaceImageService imageService;

    @GetMapping
    public RspTemplate<ImageResDto> generateImage(@Parameter String prompt) {
        return new RspTemplate<>(HttpStatus.OK, "이미지 생성 성공", imageService.generateImageBase64(prompt));
    }
}
