package supercoding.pj2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import supercoding.pj2.entity.VerificationCode;
import supercoding.pj2.entity.VerificationType;

import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    // 이메일 또는 전화번호 + 코드로 유효성 검사할 때 사용
    Optional<VerificationCode> findTopByDestinationAndCodeAndTypeOrderByCreatedAtDesc(
            String destination,
            String code,
            VerificationType type
    );

    // 최신 인증코드 조회용
    Optional<VerificationCode> findTopByDestinationAndTypeOrderByCreatedAtDesc(
            String destination,
            VerificationType type
    );
}
