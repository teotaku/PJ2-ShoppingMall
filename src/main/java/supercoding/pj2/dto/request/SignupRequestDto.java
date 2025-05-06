package supercoding.pj2.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;
    private String gender; // "M", "F"
}
