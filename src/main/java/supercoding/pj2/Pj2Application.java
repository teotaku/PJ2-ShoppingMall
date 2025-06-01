package supercoding.pj2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import supercoding.pj2.s3.AwsS3Properties;

//@EnableConfigurationProperties(AwsS3Properties.class)
@EnableJpaAuditing
@SpringBootApplication
public class Pj2Application {

	public static void main(String[] args) {

		SpringApplication.run(Pj2Application.class, args);
	}

	@Bean
	public CommandLineRunner checkAll(ApplicationContext ctx) {
		return args -> {
			System.out.println("===== 등록된 Bean 목록 =====");
			for (String name : ctx.getBeanDefinitionNames()) {
				System.out.println(name);
			}
		};
	}
}