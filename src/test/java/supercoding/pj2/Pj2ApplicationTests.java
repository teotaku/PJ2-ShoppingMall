package supercoding.pj2;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import supercoding.pj2.s3.S3Uploader;

@ActiveProfiles("test")
@SpringBootTest
class Pj2ApplicationTests {


	@Test
	void contextLoads() {
	}
	@TestConfiguration
	static class TestConfig {
		@Bean
		public S3Uploader s3Uploader() {
			return Mockito.mock(S3Uploader.class);
		}
	}
}
