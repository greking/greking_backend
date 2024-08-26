package com.greking.Greking.Common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static javax.management.Query.and;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF 설정 비활성화
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/survey/submit").permitAll() // 모든 API 엔드포인트에 인증 없이 접근 가능
                        .requestMatchers("/api/courses/addAll").permitAll()
                        .requestMatchers("/api/weather/**").permitAll()
                )
                .anonymous(withDefaults()) // 익명 사용자 허용

                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll) // 로그인 폼 접근 허용
                .logout(LogoutConfigurer::permitAll); // 로그아웃 접근 허용

        return http.build();
    }
}
