package shop.itcontest17.stdev2025_13.imageai.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import shop.itcontest17.stdev2025_13.global.template.RspTemplate;
import shop.itcontest17.stdev2025_13.imageai.api.dto.request.ImagePromptReqDto;
import shop.itcontest17.stdev2025_13.imageai.api.dto.response.ImageResDto;

@Tag(name = "[이미지 생성 API]", description = "이미지 생성 API")
public interface HuggingFaceImageDocs {

    @Operation(summary = "질문을 받아 이미지를 생성", description = "질문을 받아 이미지를 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "이미지 생성 성공",
                            content = @Content(schema = @Schema(implementation = ImageResDto.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "401", description = "인증 실패"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            })
    RspTemplate<ImageResDto> generateImage(
            @Parameter(description = "사용자 질문 받기", required = true) String prompt);

}
