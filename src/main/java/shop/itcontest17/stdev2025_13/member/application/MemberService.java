package shop.itcontest17.stdev2025_13.member.application;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itcontest17.stdev2025_13.member.api.dto.response.EmotionCountResDto;
import shop.itcontest17.stdev2025_13.member.domain.repository.MemberRepository;
import shop.itcontest17.stdev2025_13.process.domain.repository.ProcessesRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final ProcessesRepository processesRepository;

    @Transactional(readOnly = true)
    public List<EmotionCountResDto> getTop5EmotionsByEmail(String email) {
        Pageable top5 = PageRequest.of(0, 5);

        return processesRepository.findTop5EmotionsByEmail(email, top5);
    }
}
