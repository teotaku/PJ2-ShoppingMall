package supercoding.pj2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import supercoding.pj2.dto.request.VerificationRequestDto;
import supercoding.pj2.entity.VerificationCode;
import supercoding.pj2.entity.VerificationType;
import supercoding.pj2.repository.VerificationCodeRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationCodeRepository verificationCodeRepository;

    public void sendCode(VerificationRequestDto request) {
        VerificationType type = VerificationType.valueOf(request.getType().toUpperCase());
        String destination = request.getDestination();

        // 1. 최근 1분 이내 발송된 인증 요청이 있는지 확인 (스팸 방지)
        verificationCodeRepository.findTopByDestinationAndTypeOrderByCreatedAtDesc(destination, type)
                .ifPresent(latest -> {
                    if (latest.getCreatedAt().isAfter(LocalDateTime.now().minusSeconds(60))) {
                        throw new IllegalStateException("너무 자주 요청할 수 없습니다. 잠시 후 다시 시도해주세요.");
                    }
                });

        // 2. 인증 코드 생성
        String code = String.valueOf((int) (Math.random() * 900000 + 100000));  // 6자리 숫자

        // 3. 인증코드 객체 저장
        VerificationCode verification = VerificationCode.builder()
                .type(type)
                .destination(destination)
                .code(code)
                .expiresAt(LocalDateTime.now().plusMinutes(3))  // 유효기간 3분
                .build();

        verificationCodeRepository.save(verification);

        // 4. 인증 수단에 따라 전송
        if (type == VerificationType.EMAIL) {
            sendEmail(destination, code);
        } else if (type == VerificationType.PHONE) {
            sendSms(destination, code);
        }
    }

//    현재는 비즈니스 로직 테스트용 콘솔 출력만 포함되어 있어서 이렇게 표현하고,
//    실제 서비스 배포 전에는 반드시 외부 메일 or SMS API로 대체되어야 합
    private void sendEmail(String to, String code) {
        System.out.println("[이메일 발송] " + to + " → 인증코드: " + code);
    }

    private void sendSms(String to, String code) {
        System.out.println("[SMS 발송] " + to + " → 인증코드: " + code);
    }



}
