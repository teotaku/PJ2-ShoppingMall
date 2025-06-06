package supercoding.pj2.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import supercoding.pj2.dto.request.SignupRequestDto;
import supercoding.pj2.dto.request.UserRequestDto;
import supercoding.pj2.dto.response.UserResponseDto;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 20)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Provider provider = Provider.LOCAL;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Role role = Role.USER;

    @Column
    private String providerId;

    @Column(nullable = false)
    @Builder.Default
    private boolean isDeleted = false;

    @Column
    private String profileImageUrl;

    @Column
    private Long balance;

    public enum Gender {
        M, F
    }

    public enum Provider {
        LOCAL, GOOGLE, KAKAO, NAVER
    }

    public UserResponseDto toDto() {
        return UserResponseDto.builder()
                .shippingAddress(this.address)
                .email(email)
                .build();
    }

    public static User create(SignupRequestDto dto, PasswordEncoder encoder) {
        return User.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .name(dto.getName())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .gender(Gender.valueOf(dto.getGender()))
                .provider(Provider.LOCAL)
                .role(Role.USER)
                .isDeleted(false)
                .build();
    }

    public void update(UserRequestDto dto) {
        if (dto.getEmail() != null) this.email = dto.getEmail();
        if (dto.getPhoneNumber() != null) this.phone = dto.getPhoneNumber();
        if (dto.getShippingAddress() != null) this.address = dto.getShippingAddress();
        if (dto.getProfileImageUrl() != null) this.profileImageUrl = dto.getProfileImageUrl();
    }

    public void markAsDeleted() {
        this.isDeleted = true;
    }

    public void chargeBalance(Long amount) {
        if (amount > 0) this.balance += amount;
    }
}
