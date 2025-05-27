package supercoding.pj2.client.provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import supercoding.pj2.client.OAuthClient;
import supercoding.pj2.userinfo.GoogleUserInfo;
import supercoding.pj2.userinfo.OAuthUserInfo;

@Component
@RequiredArgsConstructor
public class GoogleOAuthClient implements OAuthClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String clientId = "GOOGLE_CLIENT_ID";
    private final String clientSecret = "GOOGLE_CLIENT_SECRET";
    private final String redirectUri = "http://52.79.184.1:8080/api/v1/oauth/authorization/google";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String getAccessToken(String code) {
        String url = "https://oauth2.googleapis.com/token";

        String body = UriComponentsBuilder.newInstance()
                .queryParam("code", code)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("grant_type", "authorization_code")
                .build()
                .toUriString().substring(1); // 맨 앞 ? 제거

        JsonNode response = restTemplate.postForObject(url, body, JsonNode.class);
        return response.get("access_token").asText();
    }

    @Override
    public OAuthUserInfo getUserInfo(String accessToken) {
        String url = "https://www.googleapis.com/oauth2/v2/userinfo";

        JsonNode userInfo = restTemplate.getForObject(
                UriComponentsBuilder.fromHttpUrl(url)
                        .queryParam("access_token", accessToken)
                        .build().toUri(),
                JsonNode.class
        );

        return new GoogleUserInfo(userInfo);
    }
}
