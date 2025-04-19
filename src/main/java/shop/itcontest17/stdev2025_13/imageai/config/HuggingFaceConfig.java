package shop.itcontest17.stdev2025_13.imageai.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class HuggingFaceConfig {

    @Value("${huggingface.api-key}")
    private String apiKey;

    @Value("${huggingface.model-url}")
    private String modelUrl;

    @Bean
    public WebClient huggingFaceWebClient() {
        return WebClient.builder()
                .baseUrl(modelUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/octet-stream")
                .build();
    }
}
