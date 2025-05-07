package supercoding.pj2.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UserRequestDto {

    private String email;
    private String shippingAddress;
    private String profileImageUrl;
    private String phoneNumber;




}
