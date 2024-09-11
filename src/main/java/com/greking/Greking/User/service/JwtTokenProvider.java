package com.greking.Greking.User.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;


    @Value("${jwt.expiration.days}")
    private long expirationDays;

    private long expirationTimeInMillis;


    // JWT 토큰 생성자에서 만료 시간을 계산
    public JwtTokenProvider() {
        // 일수를 밀리초로 변환
        this.expirationTimeInMillis = expirationDays * 24L * 60L * 60L * 1000L;
    }


    //JWT 토큰 생성
    public String createToken(String userId, String email){
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("userId", userId);

        Date now = new Date();
        Date validity = new Date(now.getTime() + expirationTimeInMillis);

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    //JWT 토큰에서 사용자 이메일 추출
    public String getEmailFromToken(String token){

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.parserBuilder()
                .setSigningKey(key) //secretKey 설정
                .build() //jwtParser 생성
                .parseClaimsJws(token) //토큰 파싱
                .getBody()
                .getSubject(); // subject = 사용자 이름, 이메일 => 여기선 이메일저장됨
    }


    //JWT 토큰이 유효한지 확인
    public boolean validateToken(String token){
        try{
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }
}
