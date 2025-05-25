package supercoding.pj2.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //  강제 적용되는 CORS 필터 등록 (보안 필터보다 먼저 실행됨)
    @Bean(name = "corsFilterCustom")
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0); // 가장 먼저 적용되도록
        return bean;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults()) // 🔥 이거 꼭 필요함!
                .authorizeHttpRequests(auth -> auth

                        //  Swagger 허용
                        .requestMatchers(
                                "/swagger-ui.html", "/swagger-ui/**",
                                "/v3/api-docs", "/v3/api-docs/**",
                                "/api-docs", "/api-docs/**"
                        ).permitAll()

                        //  인증 없이 접근 가능한 API
                                .requestMatchers("/api/**").permitAll()

                        // multipart POST 명시적으로 허용
                        .requestMatchers(HttpMethod.POST, "/api/v1/products").permitAll()

                        //  정적 리소스 허용
                        .requestMatchers(
                                "/", "/index.html", "/favicon.ico",
                                "/static/**", "/assets/**", "/css/**", "/js/**", "/images/**"
                        ).permitAll()
                        //  프리플라이트 OPTIONS 요청 허용
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        //  그 외는 인증 필요
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
