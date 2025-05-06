package supercoding.pj2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import supercoding.pj2.entity.User;
import supercoding.pj2.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService
{
    private final UserRepository userRepository;

    public void softDelete(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setDeleted(true);
        userRepository.save(user);
    }

    public boolean isEmailAvailable(String email) {
        return !userRepository.existsByEmail(email);
    }

}
