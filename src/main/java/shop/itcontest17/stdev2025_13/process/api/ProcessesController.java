package shop.itcontest17.stdev2025_13.process.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itcontest17.stdev2025_13.global.annotation.CurrentUserEmail;
import shop.itcontest17.stdev2025_13.global.template.RspTemplate;
import shop.itcontest17.stdev2025_13.process.api.dto.request.EmotionReqDto;
import shop.itcontest17.stdev2025_13.process.api.dto.response.EmotionResDto;
import shop.itcontest17.stdev2025_13.process.application.ProcessesService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/processes")
public class ProcessesController {

    private final ProcessesService processesService;

    @PostMapping("/emotion")
    public RspTemplate<EmotionResDto> send(@CurrentUserEmail String email,
                                           @RequestBody EmotionReqDto emotionReqDto) {
        return new RspTemplate<>(HttpStatus.OK,
                "감정 저장 성공",
                processesService.saveEmotion(email, emotionReqDto));
    }


}
