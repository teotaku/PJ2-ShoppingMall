package supercoding.pj2.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import supercoding.pj2.entity.User;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 필요시 권한 설정 추가
        return Collections.emptyList(); // or Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // DB에 암호화된 비밀번호가 저장되어 있어야 함
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // 또는 user.getId().toString() 등
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
