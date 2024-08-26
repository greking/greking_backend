package com.greking.Greking.User.service;

import com.greking.Greking.User.domain.PasswordResetToken;
import com.greking.Greking.User.domain.User;
import com.greking.Greking.User.repository.PasswordResetTokenRepository;
import com.greking.Greking.User.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetServiceImpl {

    private final PasswordResetTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public PasswordResetServiceImpl(PasswordResetTokenRepository tokenRepository, UserRepository userRepository,
                                    EmailService emailService, BCryptPasswordEncoder passwordEncoder) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public PasswordResetToken createPasswordResetToken(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Email not found.");
        }
        User user = userOptional.get();

        // 기존 토큰 삭제
        tokenRepository.deleteByUser(user);

        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(15);

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setExpiryDate(expiryDate);
        resetToken.setUser(user);

        PasswordResetToken savedToken = tokenRepository.save(resetToken);

        // 비밀번호 재설정 링크 생성 및 이메일 전송
        String resetLink = "http://localhost:8080/reset-password?token=" + token;
        String subject = "Password Reset Request";
        String text = "To reset your password, click the link below:\n" + resetLink;
        emailService.sendEmail(user.getEmail(), subject, text);

        return savedToken;
    }

    public boolean validatePasswordResetToken(String token) {
        Optional<PasswordResetToken> tokenOptional = tokenRepository.findByToken(token);
        if (tokenOptional.isEmpty()) {
            System.out.println("Invalid token: " + token);
            return false;
        }

        PasswordResetToken resetToken = tokenOptional.get();
        boolean isValid = resetToken.getExpiryDate().isAfter(LocalDateTime.now());
        System.out.println("Token validation for user " + resetToken.getUser().getEmail() + ": " + isValid);
        return isValid;
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> tokenOptional = tokenRepository.findByToken(token);
        if (!tokenOptional.isPresent()) {
            System.out.println("Invalid token: " + token);
            throw new IllegalArgumentException("Invalid token.");
        }

        PasswordResetToken resetToken = tokenOptional.get();
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            System.out.println("Token expired for user: " + resetToken.getUser().getEmail());
            throw new IllegalArgumentException("Token expired.");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        System.out.println("Password reset successful for user: " + user.getEmail());
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}

