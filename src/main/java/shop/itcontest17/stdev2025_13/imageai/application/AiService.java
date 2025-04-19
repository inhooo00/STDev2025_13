package shop.itcontest17.itcontest17.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itcontest17.itcontest17.ai.dto.AiResponseDto;

@Service
@RequiredArgsConstructor
public class AiService {

    private final ChatClient chatClient;

    // AI에게 조언 구하기
    @Transactional
    public AiResponseDto askForAdvice(){
        ChatResponse response = callChat();
        if (response == null) {
            response = callChat();
        }

        return AiResponseDto.builder()
                .answer(response.getResult().getOutput().getContent()).build();

    }

    // AI 응답 메서드
    private ChatResponse callChat() {
        return chatClient.call(
                new Prompt(
                        ("너 gpt 버전 몇이야? 3.5야 4이야. 4라면 4o야 아니면 mini야?"
                        ),
                        OpenAiChatOptions.builder()
                                .withTemperature(0.4F)
                                .withFrequencyPenalty(0.7F)
                                .withModel("gpt-4o")
                                .build()
                ));
    }

    @Transactional
    public String translateToEnglishIfNeeded(String text) {
        // GPT에게 자연스럽게 번역 요청
        String prompt = "다음 문장을 이미지 생성용 자연스러운 영어로 번역해줘. 이미 영어면 그대로 둬. 문장: \"" + text + "\"";

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
