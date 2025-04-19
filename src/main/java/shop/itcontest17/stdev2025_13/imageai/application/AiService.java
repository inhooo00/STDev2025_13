package shop.itcontest17.stdev2025_13.imageai.application;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiImageApi.OpenAiImageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AiService {

    private final ChatClient chatClient;
    private final WebClient openAiWebClient;

    @Value("${spring.ai.openai.model.dalle}")
    private String dalleModel;

    @Value("${spring.ai.openai.api-key}")
    private String openAiKey;

    @Value("${questions.translate}")
    private String translatePrompt;

    public ChatResponse callChat(String prompt) {
        return chatClient.call(
                new Prompt(
                        prompt,
                        OpenAiChatOptions.builder()
                                .withTemperature(1.0F)
                                .withFrequencyPenalty(0.6F)
                                .withPresencePenalty(1.0F)
                                .withModel("gpt-4o")
                                .build()
                )
        );
    }

    @Transactional
    public String translateToEnglishIfNeeded(String text) {
        ChatResponse response = chatClient.call(
                new Prompt(translatePrompt + text,
                        OpenAiChatOptions.builder()
                                .withModel("gpt-4o")
                                .withTemperature(0.4F)
                                .build()
                )
        );

        return response.getResult().getOutput().getContent();
    }

    @Transactional
    public String generateImageUrl(String prompt) {
        System.out.println(openAiKey);
        String translatedPrompt = translateToEnglishIfNeeded(prompt)
                .replace("\"", "\\\"")
                .replace("\n", " ");

        log.info("최종 전송 프롬프트: {}", translatedPrompt);

        // JSON 객체 생성
        Map<String, Object> requestBody = Map.of(
                "model", "dall-e-3",
                "prompt", translatedPrompt,
                "n", 1,
                "size", "1024x1024",
                "quality", "standard",
                "style", "vivid"
        );

        String responseUrl = openAiWebClient.post()
                .uri("https://api.openai.com/v1/images/generations")
                .header("Authorization", "Bearer " + openAiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(OpenAiImageResponse.class)
                .map(res -> res.data().get(0).url())
                .doOnError(e -> log.error("이미지 생성 중 오류 발생", e))
                .block();

        if (responseUrl == null) {
            throw new RuntimeException("Image generation failed.");
        }

        return responseUrl;
    }

}
