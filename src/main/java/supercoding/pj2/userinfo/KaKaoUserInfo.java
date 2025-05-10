package supercoding.pj2.userinfo;

import com.fasterxml.jackson.databind.JsonNode;
import supercoding.pj2.entity.Provider;
import supercoding.pj2.entity.User;

public class KaKaoUserInfo extends OAuthUserInfo {

    private final JsonNode attributes;

    public KaKaoUserInfo(JsonNode attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getEmail() {
        return attributes.get("email").asText();
    }

    @Override
    public String getName() {
        return attributes.get("name").asText();
    }

    @Override
    public String getProvider() {
        return "KAKAO";
    }

    @Override
    public User toEntity() {
        return User.builder()
                .email(getEmail())
                .name(getName())
                .provider(User.Provider.KAKAO)
                .providerId(getProvider())
                .password("") // 소셜 로그인 사용자는 비번 없음
                .isDeleted(false)
                .build();
    }
}
