package shop.itcontest17.stdev2025_13.process.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itcontest17.stdev2025_13.global.annotation.CurrentUserEmail;
import shop.itcontest17.stdev2025_13.global.template.RspTemplate;
import shop.itcontest17.stdev2025_13.imageai.api.dto.response.ImageResDto;
import shop.itcontest17.stdev2025_13.process.api.dto.request.EmotionReqDto;
import shop.itcontest17.stdev2025_13.process.api.dto.request.SubmitAnswerReqDto;
import shop.itcontest17.stdev2025_13.process.api.dto.response.EmotionResDto;
import shop.itcontest17.stdev2025_13.process.api.dto.response.GenerateQuestionResDto;
import shop.itcontest17.stdev2025_13.process.api.dto.response.SubmitAnswerResDto;
import shop.itcontest17.stdev2025_13.process.api.dto.response.SummaryResDto;
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

    @PatchMapping("{processId}/question")
    public RspTemplate<GenerateQuestionResDto> updateQuestion(@PathVariable Long processId) {
        return new RspTemplate<>(HttpStatus.OK,
                "질문 저장 성공",
                processesService.updateQuestion(processId));
    }

    @PatchMapping("{processId}/first-result")
    public RspTemplate<SubmitAnswerResDto> updateFirstResult(@PathVariable Long processId,
                                                             @RequestBody SubmitAnswerReqDto submitAnswerReqDto) {
        return new RspTemplate<>(HttpStatus.OK,
                "ai가 반환한 결과 저장 성공",
                processesService.submitAnswer(processId, submitAnswerReqDto));
    }

    @PatchMapping("{processId}/image")
    public RspTemplate<ImageResDto> updateImage(@PathVariable Long processId) {
        return new RspTemplate<>(HttpStatus.OK,
                "ai가 반환한 이미지 저장 성공",
                processesService.generateImage(processId));
    }

    @PatchMapping("{processId}/summary")
    public RspTemplate<SummaryResDto> updateSummary(@PathVariable Long processId) {
        return new RspTemplate<>(HttpStatus.OK,
                "ai가 반환한 이미지 저장 성공",
                processesService.updateSummary(processId));
    }
}
