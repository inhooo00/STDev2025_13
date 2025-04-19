package shop.itcontest17.stdev2025_13.global.error.dto;

public record ErrorResponse(
        int statusCode,
        String message
) {
}