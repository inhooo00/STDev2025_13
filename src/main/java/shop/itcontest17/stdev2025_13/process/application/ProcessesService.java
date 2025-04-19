package shop.itcontest17.stdev2025_13.process.application;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itcontest17.stdev2025_13.global.entity.Status;
import shop.itcontest17.stdev2025_13.imageai.application.AiService;
import shop.itcontest17.stdev2025_13.member.domain.Member;
import shop.itcontest17.stdev2025_13.member.domain.repository.MemberRepository;
import shop.itcontest17.stdev2025_13.member.exception.MemberNotFoundException;
import shop.itcontest17.stdev2025_13.process.api.dto.request.EmotionReqDto;
import shop.itcontest17.stdev2025_13.process.api.dto.request.GenerateQuestionReqDto;
import shop.itcontest17.stdev2025_13.process.api.dto.response.EmotionResDto;
import shop.itcontest17.stdev2025_13.process.api.dto.response.GenerateQuestionResDto;
import shop.itcontest17.stdev2025_13.process.domain.Processes;
import shop.itcontest17.stdev2025_13.process.domain.repository.ProcessesRepository;
import shop.itcontest17.stdev2025_13.process.exception.ProcessesNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProcessesService {

    private final ProcessesRepository processesRepository;
    private final MemberRepository memberRepository;
    private final AiService aiService;

    @Value("${questions.question}")
    private String questionPrompt;

    @Transactional
    public EmotionResDto saveEmotion(String email, EmotionReqDto emotionReqDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        Processes process = Processes.builder()
                .member(member)
                .status(Status.ACTIVE)
                .emotion(emotionReqDto.emotion())
                .question("")  // 또는 null
                .answer("")
                .firstResult("")
                .summary("")
                .build();

        Processes saved = processesRepository.save(process);

        return new EmotionResDto(saved.getEmotion(), saved.getId());
    }

    @Transactional
    public GenerateQuestionResDto updateQuestion(Long processId) {
        Processes process = processesRepository.findById(processId)
                .orElseThrow(ProcessesNotFoundException::new);

        ChatResponse chatResponse = aiService.callChat(questionPrompt + process.getEmotion());

        process.updateQuestion(chatResponse.getResult().getOutput().getContent());

        Processes updated = processesRepository.save(process);

        return new GenerateQuestionResDto(updated.getQuestion());
    }


}
