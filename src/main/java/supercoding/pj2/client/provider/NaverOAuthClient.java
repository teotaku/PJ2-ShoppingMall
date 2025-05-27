package supercoding.pj2.client.provider;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import supercoding.pj2.client.OAuthClient;
import supercoding.pj2.userinfo.NaverUserInfo;
import supercoding.pj2.userinfo.OAuthUserInfo;

@Component
@RequiredArgsConstructor
public class NaverOAuthClient implements OAuthClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String clientId = "NAVER_CLIENT_ID";
    private final String clientSecret = "NAVER_CLIENT_SECRET";
    private final String redirectUri = "http://52.79.184.1:8080/api/v1/oauth/authorization/naver";

    @Override
    public String getAccessToken(String code) {
        String url = UriComponentsBuilder.fromHttpUrl("https://nid.naver.com/oauth2.0/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("code", code)
                .queryParam("state", "random")  // 실제 서비스에선 state 검증 필요
                .build().toUriString();

        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
        return response.getBody().get("access_token").asText();
    }

    @Override
    public OAuthUserInfo getUserInfo(String accessToken) {
        String url = "https://openapi.naver.com/v1/nid/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url, HttpMethod.GET, request, JsonNode.class
        );

        return new NaverUserInfo(response.getBody().get("response")); // Naver는 "response" 안에 유저 정보가 있음
    }
}
