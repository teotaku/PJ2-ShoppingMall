package supercoding.pj2.s3;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "cloud.aws")
public class AwsS3Properties {

    private Credentials credentials;
    private Region region;
    private S3 s3;

    @Getter
    @Setter
    public static class Credentials {
        private String accessKey;
        private String secretKey;
    }

    @Getter
    @Setter
    public static class Region {
        private String staticRegion;
    }

    @Getter
    @Setter
    public static class S3 {
        private String bucket;
    }
}
