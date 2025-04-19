package shop.itcontest17.stdev2025_13.process.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itcontest17.stdev2025_13.global.entity.Status;
import shop.itcontest17.stdev2025_13.imageai.api.dto.response.ImageResDto;
import shop.itcontest17.stdev2025_13.imageai.application.AiService;
import shop.itcontest17.stdev2025_13.imageai.application.HuggingFaceImageService;
import shop.itcontest17.stdev2025_13.member.domain.Member;
import shop.itcontest17.stdev2025_13.member.domain.repository.MemberRepository;
import shop.itcontest17.stdev2025_13.member.exception.MemberNotFoundException;
import shop.itcontest17.stdev2025_13.process.api.dto.request.EmotionReqDto;
import shop.itcontest17.stdev2025_13.process.api.dto.request.GenerateQuestionReqDto;
import shop.itcontest17.stdev2025_13.process.api.dto.request.SubmitAnswerReqDto;
import shop.itcontest17.stdev2025_13.process.api.dto.response.EmotionResDto;
import shop.itcontest17.stdev2025_13.process.api.dto.response.GenerateQuestionResDto;
import shop.itcontest17.stdev2025_13.process.api.dto.response.SubmitAnswerResDto;
import shop.itcontest17.stdev2025_13.process.api.dto.response.SummaryResDto;
import shop.itcontest17.stdev2025_13.process.domain.Processes;
import shop.itcontest17.stdev2025_13.process.domain.repository.ProcessesRepository;
import shop.itcontest17.stdev2025_13.process.exception.ProcessesNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProcessesService {

    private final ProcessesRepository processesRepository;
    private final MemberRepository memberRepository;
    private final AiService aiService;
    private final HuggingFaceImageService huggingFaceImageService;

    @Value("${questions.question}")
    private String questionPrompt;

    @Value("${questions.first-result}")
    private String firstResultPrompt;

    @Value("${questions.image}")
    private String imagePrompt;

    @Value("${questions.summary}")
    private String summaryPrompt;

    @Transactional
    public EmotionResDto saveEmotion(String email, EmotionReqDto emotionReqDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        ChatResponse chatResponse = aiService.callChat(questionPrompt + emotionReqDto.emotion());

        Processes process = Processes.builder()
                .member(member)
                .status(Status.ACTIVE)
                .emotion(emotionReqDto.emotion())
                .question(chatResponse.getResult().getOutput().getContent())
                .answer("")
                .firstResult("")
                .summary("")
                .build();

        Processes saved = processesRepository.save(process);

        return new EmotionResDto(saved.getEmotion(), saved.getId(), saved.getQuestion());
    }

    @Transactional
    public SubmitAnswerResDto submitAnswer(Long processId, SubmitAnswerReqDto reqDto) {
        Processes process = processesRepository.findById(processId)
                .orElseThrow(ProcessesNotFoundException::new);

        // 유저 답변 저장
        process.updateAnswer(reqDto.answer());

        ChatResponse chatResponse = aiService.callChat(
                firstResultPrompt + process.getQuestion() + "\n"
                        + "답변은 : " + process.getAnswer());

        // AI 답변 저장
        process.updateFirstResult(chatResponse.getResult().getOutput().getContent());

        return new SubmitAnswerResDto(process.getFirstResult());
    }

    @Transactional
    public ImageResDto generateImage(Long processId) {
        Processes process = processesRepository.findById(processId)
                .orElseThrow(ProcessesNotFoundException::new);

        String bettaPrompt =
                "내 감정은:" + process.getEmotion() + "\n"
                        + "내 질문은:" + process.getEmotion() + "\n"
                        + "내 대답은:" + process.getAnswer() + "\n"
                        + "AI의 결론은:" + process.getFirstResult() + "\n"
                        + imagePrompt;

        ChatResponse chatResponse = aiService.callChat(bettaPrompt);
        log.info(chatResponse.getResult().getOutput().getContent());

        process.updateImage(
                huggingFaceImageService.generateImageBase64(
                                aiService.translateToEnglishIfNeeded(
                                        chatResponse.getResult().getOutput().getContent())).getBase64());

        return new ImageResDto(process.getImage());
    }

    @Transactional
    public SummaryResDto updateSummary(Long processId) {
        Processes process = processesRepository.findById(processId)
                .orElseThrow(ProcessesNotFoundException::new);

        ChatResponse chatResponse = aiService.callChat(
                "내 감정은:" + process.getEmotion() + "\n"
                        + "내 질문은:" + process.getEmotion() + "\n"
                        + "내 대답은:" + process.getAnswer() + "\n"
                        + "AI의 결론은:" + process.getFirstResult() + "\n"
                        + summaryPrompt);

        process.updateSummary(chatResponse.getResult().getOutput().getContent());

        return new SummaryResDto(process.getSummary());
    }
}
