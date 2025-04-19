package shop.itcontest17.stdev2025_13.process.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itcontest17.stdev2025_13.global.entity.Status;
import shop.itcontest17.stdev2025_13.member.domain.Member;
import shop.itcontest17.stdev2025_13.member.domain.repository.MemberRepository;
import shop.itcontest17.stdev2025_13.member.exception.MemberNotFoundException;
import shop.itcontest17.stdev2025_13.process.api.dto.request.EmotionReqDto;
import shop.itcontest17.stdev2025_13.process.api.dto.response.EmotionResDto;
import shop.itcontest17.stdev2025_13.process.domain.Processes;
import shop.itcontest17.stdev2025_13.process.domain.repository.ProcessesRepository;

@Service
@RequiredArgsConstructor
public class ProcessesService {

    private final ProcessesRepository processesRepository;
    private final MemberRepository memberRepository;

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
}
