package shop.itcontest17.stdev2025_13.member.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itcontest17.stdev2025_13.global.annotation.CurrentUserEmail;
import shop.itcontest17.stdev2025_13.global.template.RspTemplate;
import shop.itcontest17.stdev2025_13.member.api.dto.request.EmotionReqDto;
import shop.itcontest17.stdev2025_13.member.api.dto.request.ImageReqDto;
import shop.itcontest17.stdev2025_13.member.api.dto.response.EmotionCountResDto;
import shop.itcontest17.stdev2025_13.member.api.dto.response.ImageResDto;
import shop.itcontest17.stdev2025_13.member.api.dto.response.MemberNameResDto;
import shop.itcontest17.stdev2025_13.member.api.dto.response.ProcessDetail;
import shop.itcontest17.stdev2025_13.member.application.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/members")
public class MemberController implements MemberDocs{

    private final MemberService memberService;


    @GetMapping("/top-emotions")
    public RspTemplate<List<EmotionCountResDto>> getTopEmotions(@CurrentUserEmail String email) {
        return new RspTemplate<>(HttpStatus.OK,
                "탑 5개 감정반환 성공",
                memberService.getTop5EmotionsByEmail(email));
    }

    @GetMapping("/my-images")
    public RspTemplate<List<ImageResDto>> getMyImages(@CurrentUserEmail String email,
                                                      @RequestBody EmotionReqDto emotionReqDto) {
        return new RspTemplate<>(HttpStatus.OK,
                "내가 생성한 이미지 반환 성공",
                memberService.getImagesByEmotion(email,emotionReqDto));
    }

    @GetMapping("/process-detail")
    public RspTemplate<ProcessDetail> getProcessDetail(@RequestBody ImageReqDto imageReqDto) {
        return new RspTemplate<>(HttpStatus.OK,
                "프로세스 상세정보 반환 성공",
                memberService.getProcessDetailByImage(imageReqDto));
    }

    @GetMapping("/name")
    public RspTemplate<MemberNameResDto> getName(@CurrentUserEmail String email) {
        return new RspTemplate<>(HttpStatus.OK,
                "이름 반환 성공",
                memberService.getMemberNameByEmail(email));
    }
}