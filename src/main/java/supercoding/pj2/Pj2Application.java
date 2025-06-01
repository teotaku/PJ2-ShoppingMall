package supercoding.pj2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import supercoding.pj2.s3.AwsS3Properties;

@EnableConfigurationProperties(AwsS3Properties.class)
@EnableJpaAuditing
@SpringBootApplication
public class Pj2Application {

	public static void main(String[] args) {
		String accessKey = System.getenv("AWS_ACCESS_KEY");
		String secretKey = System.getenv("AWS_SECRET_KEY");

		if (accessKey != null) {
			System.setProperty("AWS_ACCESS_KEY", accessKey);
		} else {
			System.out.println(" AWS_ACCESS_KEY 환경변수가 설정되지 않았습니다.");
		}

		if (secretKey != null) {
			System.setProperty("AWS_SECRET_KEY", secretKey);
		} else {
			System.out.println(" AWS_SECRET_KEY 환경변수가 설정되지 않았습니다.");
		}

		SpringApplication.run(Pj2Application.class, args);
	}
}
