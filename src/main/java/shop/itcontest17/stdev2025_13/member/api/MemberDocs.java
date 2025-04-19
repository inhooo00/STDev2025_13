package shop.itcontest17.stdev2025_13.member.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import shop.itcontest17.stdev2025_13.global.template.RspTemplate;
import shop.itcontest17.stdev2025_13.member.api.dto.request.EmotionReqDto;
import shop.itcontest17.stdev2025_13.member.api.dto.request.SummaryTitleReqDto;
import shop.itcontest17.stdev2025_13.member.api.dto.response.EmotionCountResDto;
import shop.itcontest17.stdev2025_13.member.api.dto.response.ArchiveResDto;
import shop.itcontest17.stdev2025_13.member.api.dto.response.MemberNameResDto;
import shop.itcontest17.stdev2025_13.member.api.dto.response.ProcessDetail;

@Tag(name = "[멤버 API]", description = "멤버 관련 API")
public interface MemberDocs {

    @Operation(summary = "유저 감정 상위 5개 조회", description = "유저의 감정 데이터를 기반으로 상위 5개 감정을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "감정 목록 조회 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmotionCountResDto.class)))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "401", description = "인증 실패"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            })
    RspTemplate<List<EmotionCountResDto>> getTopEmotions(
            @Parameter(description = "로그인한 유저 이메일 (JWT에서 추출)", hidden = true) String email);

    @Operation(summary = "감정으로 생성한 이미지 목록 조회", description = "선택한 감정에 해당하는 생성된 이미지를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "이미지 조회 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ArchiveResDto.class)))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "401", description = "인증 실패"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            })
    RspTemplate<List<ArchiveResDto>> getMyImages(
            @Parameter(description = "로그인한 유저 이메일 (JWT에서 추출)", hidden = true) String email,
            @Parameter(description = "감정 요청 DTO", required = true) EmotionReqDto emotionReqDto);

    @Operation(summary = "이미지로 프로세스 상세 정보 조회", description = "이미지를 기준으로 해당 프로세스의 상세 정보를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "프로세스 상세 조회 성공",
                            content = @Content(schema = @Schema(implementation = ProcessDetail.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "401", description = "인증 실패"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            })
    RspTemplate<ProcessDetail> getProcessDetail(
            @Parameter(description = "이미지 요청 DTO", required = true) SummaryTitleReqDto summaryTitleReqDto);

    @Operation(summary = "유저 이름 조회", description = "유저 이름을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "유저 이름 조회 성공",
                            content = @Content(schema = @Schema(implementation = MemberNameResDto.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "401", description = "인증 실패"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            })
    RspTemplate<MemberNameResDto> getName(
            @Parameter(description = "로그인한 유저 이메일 (JWT에서 추출)", hidden = true) String email);
}
