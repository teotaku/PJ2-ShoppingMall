package supercoding.pj2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import supercoding.pj2.entity.User;

@Getter
@AllArgsConstructor
public class OAuthResponseDto {
    private String token;
    private User user;
}
