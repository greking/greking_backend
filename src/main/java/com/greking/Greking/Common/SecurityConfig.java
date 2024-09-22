package com.greking.Greking.Common;

import com.greking.Greking.User.service.JwtAuthenticationFilter;
import com.greking.Greking.User.service.JwtTokenProvider;
import com.greking.Greking.User.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable) //rest api 사용하므로 basic auth설정 비활성화
                .csrf(AbstractHttpConfigurer::disable) // CSRF 설정 비활성화
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //jwt토큰 사용하기 때문에 세션 사용X
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/weather/**").permitAll()
                        .requestMatchers("/api/restaurant/**").permitAll()
                        .requestMatchers("/api/courses/addAll").permitAll()

                        .requestMatchers("/api/users/register").permitAll() //회원가입은 인증되지 않은 사용자가 사용해야하므로 permitAll을 선언해야함
                        .requestMatchers(HttpMethod.GET,"/api/users/{userId}").permitAll()
                        .requestMatchers("/api/users/login").permitAll()
                        .requestMatchers("/api/survey/submit").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/courses/getCourse/{mountainName}").permitAll()

                        .requestMatchers("/api/users/{userId}/my-courses/{courseId}").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/users/{userId}/my-courses").permitAll()

                        .requestMatchers("/api/users/{userId}/my-courses/{userCourseId}").permitAll()

                        .requestMatchers("/api/users/{userId}/my-courses/{userCourseId}/complete").permitAll()

                        .requestMatchers("/api/review/{userId}/{userCourseId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/review/{userId}/{userCourseId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/review/all").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/review/{courseId}").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/recommend/{userId}").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/users/validate/{nickname}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/recommend/{userId}").permitAll()

                        .requestMatchers("/api/courses/save-image-path").permitAll()
                        .requestMatchers(HttpMethod.GET, "api/courses/image/{courseName}").permitAll()
                        .requestMatchers(HttpMethod.GET ).permitAll()

                        .requestMatchers("/api/gpx/upload_directory").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/gpx/{courseName}").permitAll()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, userService),
                        UsernamePasswordAuthenticationFilter.class)
                .anonymous(withDefaults()) // 익명 사용자 허용
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll) // 로그인 폼 접근 허용
                .logout(LogoutConfigurer::permitAll); // 로그아웃 접근 허용

        return http.build();
    }
}
