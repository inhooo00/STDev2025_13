package shop.itcontest17.stdev2025_13.imageai.application;

import io.jsonwebtoken.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import shop.itcontest17.stdev2025_13.s3.application.AwsS3Service;
import shop.itcontest17.stdev2025_13.s3.config.BytesToMultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AiService {

    private final ChatClient chatClient;
    private final WebClient openAiWebClient;
    private final AwsS3Service awsS3Service;
    private final WebClient webClient;

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
        String translatedPrompt = translateToEnglishIfNeeded(prompt)
                .replace("\"", "\\\"")
                .replace("\n", " ");

        log.info("최종 전송 프롬프트: {}", translatedPrompt);

        Map<String, Object> requestBody = Map.of(
                "model", "dall-e-3",
                "prompt", translatedPrompt,
                "n", 1,
                "size", "1024x1024",
                "quality", "standard",
                "style", "vivid"
        );

        // 1. OpenAI 이미지 생성 요청
        String openAiImageUrl = openAiWebClient.post()
                .uri("https://api.openai.com/v1/images/generations")
                .header("Authorization", "Bearer " + openAiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(OpenAiImageResponse.class)
                .map(res -> res.data().get(0).url())
                .doOnError(e -> log.error("이미지 생성 중 오류 발생", e))
                .block();

        if (openAiImageUrl == null) {
            throw new RuntimeException("Image generation failed.");
        }

        // 2. 이미지 다운로드 (403 방지를 위한 Java 기본 방식)
        byte[] imageBytes;
        try (InputStream inputStream = new URL(openAiImageUrl).openStream()) {
            imageBytes = inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("이미지 다운로드 실패", e);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }

        if (imageBytes.length == 0) {
            throw new RuntimeException("다운로드된 이미지가 비어 있습니다.");
        }

        // 3. MultipartFile 변환 후 S3 업로드
        MultipartFile multipartImage = new BytesToMultipartFile("generated.png", "image/png", imageBytes);
        List<String> uploadedFileNames = awsS3Service.uploadFile(List.of(multipartImage));
        String storedFileName = uploadedFileNames.get(0);

        // 4. S3 URL 생성
        return awsS3Service.getFileUrls(storedFileName);
    }
}
