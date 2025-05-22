package supercoding.pj2;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Pj2Application {

	public static void main(String[] args) {
		System.setProperty("AWS_ACCESS_KEY", System.getenv("AWS_ACCESS_KEY"));
		System.setProperty("AWS_SECRET_KEY", System.getenv("AWS_SECRET_KEY"));

		SpringApplication.run(Pj2Application.class, args);

	}

}
