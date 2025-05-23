package supercoding.pj2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import supercoding.pj2.entity.User;
import supercoding.pj2.exception.NotFoundException;
import supercoding.pj2.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void softDelete(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.markAsDeleted();
        userRepository.save(user);
    }


    public boolean isEmailAvailable(String email) {
        return !userRepository.existsByEmail(email);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()->new NotFoundException("유저를 찾을 수 없습니다."));
    }
}
