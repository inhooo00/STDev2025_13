package shop.itcontest17.stdev2025_13.member.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itcontest17.stdev2025_13.global.annotation.CurrentUserEmail;
import shop.itcontest17.stdev2025_13.global.template.RspTemplate;
import shop.itcontest17.stdev2025_13.member.api.dto.response.EmotionCountResDto;
import shop.itcontest17.stdev2025_13.member.application.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/top-emotions")
    public RspTemplate<List<EmotionCountResDto>> getTopEmotions(@CurrentUserEmail String email) {
        return new RspTemplate<>(HttpStatus.OK,
                "탑 5개 감정반환 성공",
                memberService.getTop5EmotionsByEmail(email));
    }
}