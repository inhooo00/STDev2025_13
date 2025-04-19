package shop.itcontest17.itcontest17.stable.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Base64;
import shop.itcontest17.itcontest17.ai.AiService;

@Service
@RequiredArgsConstructor
public class HuggingFaceImageService {

    private final WebClient huggingFaceWebClient;
    private final AiService aiService;

    public String generateImageBase64(String prompt) {
        String translatedPrompt = aiService.translateToEnglishIfNeeded(prompt);
        System.out.println(translatedPrompt);
        byte[] imageBytes = huggingFaceWebClient.post()
                .bodyValue(new TextToImageRequest(translatedPrompt))
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
