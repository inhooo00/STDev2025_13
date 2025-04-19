package shop.itcontest17.stdev2025_13.imageai.application;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AiService {

    private final ChatClient chatClient;

    @Value("${questions.translate}")
    private String translatePrompt;

    private ChatResponse callChat(String prompt) {
        return chatClient.call(
                new Prompt(
                        prompt,
                        OpenAiChatOptions.builder()
                                .withTemperature(1F)
                                .withFrequencyPenalty(0.6F)
                                .withPresencePenalty(1F)
                                .withModel("gpt-4o")
                                .build()
                ));
    }

    @Transactional
    public String translateToEnglishIfNeeded(String text) {
        // 번역 요청
        String prompt = translatePrompt + text;

        ChatResponse response = chatClient.call(
                new Prompt(prompt,
                OpenAiChatOptions.builder()
                        .withModel("gpt-4o")
                        .withTemperature(0.4F)
                        .build()
        ));

        return response.getResult().getOutput().getContent();
    }
}
