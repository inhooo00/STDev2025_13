package shop.itcontest17.stdev2025_13.process.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import shop.itcontest17.stdev2025_13.global.template.RspTemplate;
import shop.itcontest17.stdev2025_13.imageai.api.dto.response.ImageResDto;
import shop.itcontest17.stdev2025_13.process.api.dto.request.EmotionReqDto;
import shop.itcontest17.stdev2025_13.process.api.dto.request.SubmitAnswerReqDto;
import shop.itcontest17.stdev2025_13.process.api.dto.response.EmotionResDto;
import shop.itcontest17.stdev2025_13.process.api.dto.response.GenerateQuestionResDto;
import shop.itcontest17.stdev2025_13.process.api.dto.response.SubmitAnswerResDto;
import shop.itcontest17.stdev2025_13.process.api.dto.response.SummaryResDto;

@Tag(name = "[프로세스 API]", description = "프로세스 관련 API")
public interface ProcessesDocs {

    @Operation(summary = "감정으로 프로세스를 시작", description = "감정으로 프로세스를 시작합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "감정 저장 성공",
                            content = @Content(schema = @Schema(implementation = EmotionResDto.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "401", description = "인증 실패"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            })
    RspTemplate<EmotionResDto> createProcesses(
            @Parameter(description = "로그인한 유저의 이메일(토큰에서 자동 추출)", hidden = true) String email,
            @Parameter(description = "감정 정보", required = true) EmotionReqDto emotionReqDto);

    @Operation(summary = "답변을 받아서 과학적 사실 생성", description = "답변을 받앟서 과학적 사실 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "답변, 과학적 사실 저장 성공",
                            content = @Content(schema = @Schema(implementation = SubmitAnswerResDto.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "401", description = "인증 실패"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            })
    RspTemplate<SubmitAnswerResDto> updateFirstResult(
            @Parameter(description = "프로세스 id", required = true) Long processId,
            @Parameter(description = "유저 답변 정보", required = true) SubmitAnswerReqDto submitAnswerReqDto);

    @Operation(summary = "이미지 생성", description = "이미지를 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "이미지 저장 성공",
                            content = @Content(schema = @Schema(implementation = ImageResDto.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "401", description = "인증 실패"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            })
    RspTemplate<ImageResDto> updateImage(
            @Parameter(description = "프로세스 id", required = true)  Long processId);

    @Operation(summary = "요약본 생성", description = "요약본을 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "요약본 저장 성공",
                            content = @Content(schema = @Schema(implementation = SummaryResDto.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "401", description = "인증 실패"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            })
    RspTemplate<SummaryResDto> updateSummary(
            @Parameter(description = "프로세스 id", required = true) Long processId);
}
