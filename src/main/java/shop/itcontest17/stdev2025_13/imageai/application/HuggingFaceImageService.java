package shop.itcontest17.stdev2025_13.imageai.application;

import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import shop.itcontest17.stdev2025_13.imageai.api.dto.request.TextToImageReqDto;

@Service
@RequiredArgsConstructor
public class HuggingFaceImageService {

    private final WebClient huggingFaceWebClient;
    private final AiService aiService;

    public String generateImageBase64(String prompt) {
        String translatedPrompt = aiService.translateToEnglishIfNeeded(prompt);
        System.out.println(translatedPrompt);
        byte[] imageBytes = huggingFaceWebClient.post()
                .bodyValue(new TextToImageReqDto(translatedPrompt))
                .retrieve()
                .bodyToMono(byte[].class)
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.empty();
                })
                .block();

        if (imageBytes == null) {
            throw new RuntimeException("Image generation failed.");
        }

        return Base64.getEncoder().encodeToString(imageBytes);
    }
}
