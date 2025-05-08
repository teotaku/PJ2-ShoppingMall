package supercoding.pj2.client;

import supercoding.pj2.userinfo.OAuthUserInfo;

public interface OAuthClient {
    String getAccessToken(String code);
    OAuthUserInfo getUserInfo(String accessToken);
}
