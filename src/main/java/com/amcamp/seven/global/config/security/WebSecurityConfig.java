package com.amcamp.seven.global.config.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // JWT 사용을 위해 CSRF, 세션 비활성화
                .cors(withDefaults())
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable) // oauth 사용 예정으로 비활성화
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(
                auth ->
                        auth.requestMatchers("/actuator/**")
                                .permitAll()
                                .requestMatchers("**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()); // actuator 경로 모두 접근 가능, 이외는 인증된 사용자만 가능

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedHeader("*"); // 모든 헤더를 허용
        configuration.addAllowedMethod("*"); // 모든 HTTP 메서드를 허용 (GET, POST 등)
        configuration.setAllowCredentials(true); // 자격 증명(쿠키 등)을 허용
        configuration.setMaxAge(3600L); // CORS preflight 요청에 대한 캐시 시간을 설정 (초 단위)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 URL 패턴에 CORS 설정 적용
        return source;
    }
}
