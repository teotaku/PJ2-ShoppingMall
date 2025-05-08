package supercoding.pj2.userinfo;

import supercoding.pj2.entity.User;

public abstract class OAuthUserInfo {
    public abstract String getEmail();
    public abstract String getName();
    public abstract String getProvider();
    public abstract User toEntity();
}
