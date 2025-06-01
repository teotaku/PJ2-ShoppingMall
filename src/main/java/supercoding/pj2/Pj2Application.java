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

		SpringApplication.run(Pj2Application.class, args);
	}
}
