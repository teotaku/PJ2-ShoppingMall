package supercoding.pj2.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
@AllArgsConstructor
@Builder
@Getter
public class UserResponseDto {

    private String email;
    private String shippingAddress;
    private RecentOrderDto recentOrderDto;
}
