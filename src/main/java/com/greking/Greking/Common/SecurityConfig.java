package com.greking.Greking.Common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 설정 비활성화
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/survey/submit").permitAll() // 모든 API 엔드포인트에 인증 없이 접근 가능
                        .requestMatchers("/api/courses/addAll").permitAll()
                        .anyRequest().authenticated()
                )
                .anonymous(withDefaults()) // 익명 사용자 허용
                .formLogin(formLogin -> formLogin.permitAll()) // 로그인 폼 접근 허용
                .logout(logout -> logout.permitAll()); // 로그아웃 접근 허용
        return http.build();
    }
}
