package supercoding.pj2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerificationCode extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationType type;

    @Column(nullable = false)
    private String destination; // 이메일 or 전화번호

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    @Builder.Default
    private boolean verified = false;

    public static VerificationCode create(String destination, String code, VerificationType type) {
        return VerificationCode.builder()
                .type(type)
                .destination(destination)
                .code(code)
                .expiresAt(LocalDateTime.now().plusMinutes(3))
                .build();
    }

}
