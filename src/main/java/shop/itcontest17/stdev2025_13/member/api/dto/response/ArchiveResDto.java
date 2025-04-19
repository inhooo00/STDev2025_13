package shop.itcontest17.stdev2025_13.member.api.dto.response;

import java.time.LocalDateTime;

public record ArchiveResDto(
        LocalDateTime createdAt,
        String summaryTitle
) {
}
