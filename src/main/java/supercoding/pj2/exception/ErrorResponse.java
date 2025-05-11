package supercoding.pj2.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ErrorResponse {
    @Schema(description = "HTTP 상태 코드", example = "404")
    private int status;
    @Schema(description = "에러 메시지", example = "장바구니가 없습니다.")
    private String message;
    @Schema(description = "에러 발생 시각", example = "2025-05-11T14:25:00")
    private LocalDateTime timestmap;


    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestmap = LocalDateTime.now();
    }
}
