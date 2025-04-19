package shop.itcontest17.stdev2025_13.member.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itcontest17.stdev2025_13.member.api.dto.request.EmotionReqDto;
import shop.itcontest17.stdev2025_13.member.api.dto.request.SummaryTitleReqDto;
import shop.itcontest17.stdev2025_13.member.api.dto.response.EmotionCountResDto;
import shop.itcontest17.stdev2025_13.member.api.dto.response.ArchiveResDto;
import shop.itcontest17.stdev2025_13.member.api.dto.response.MemberNameResDto;
import shop.itcontest17.stdev2025_13.member.api.dto.response.ProcessDetail;
import shop.itcontest17.stdev2025_13.member.domain.Member;
import shop.itcontest17.stdev2025_13.member.domain.repository.MemberRepository;
import shop.itcontest17.stdev2025_13.member.exception.MemberNotFoundException;
import shop.itcontest17.stdev2025_13.process.domain.Processes;
import shop.itcontest17.stdev2025_13.process.domain.repository.ProcessesRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final ProcessesRepository processesRepository;
    private final MemberRepository memberRepository;

    public List<EmotionCountResDto> getTop5EmotionsByEmail(String email) {
        Pageable top5 = PageRequest.of(0, 5);

        return processesRepository.findTop5EmotionsByEmail(email, top5);
    }

    public List<ArchiveResDto> getSummarysByEmotion(String email, EmotionReqDto emotionReqDto) {
        return processesRepository.findSummaryTitleByEmailAndEmotion(email, emotionReqDto.emotion());
    }

    public ProcessDetail getProcessDetailBySummaryTitle(SummaryTitleReqDto summaryTitleReqDto) {
        Processes processes = processesRepository.findBySummaryTitle(summaryTitleReqDto.summaryTitle());

        return ProcessDetail.builder()
                .emotion(processes.getEmotion())
                .question(processes.getQuestion())
                .answer(processes.getAnswer())
                .summary(processes.getSummary())
                .image(processes.getImage())
                .firstResult(processes.getFirstResult())
                .summaryTitle(processes.getSummaryTitle())
                .build();
    }

    public List<ArchiveResDto> getProcessDetailByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        List<Processes> processesList = processesRepository.findAllByMember(member);

        return processesList.stream()
                .map(processes -> ArchiveResDto.builder()
                        .createdAt(processes.getCreatedAt())
                        .summaryTitle(processes.getSummaryTitle())
                        .build())
                .toList();
    }


    public MemberNameResDto getMemberNameByEmail(String email) {
        return memberRepository.findNameByEmail(email);
    }
}
