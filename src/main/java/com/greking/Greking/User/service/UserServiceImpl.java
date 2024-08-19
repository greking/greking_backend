package com.greking.Greking.User.service;

import com.greking.Greking.User.domain.PasswordResetToken;
import com.greking.Greking.User.domain.User;
import com.greking.Greking.User.repository.PasswordResetTokenRepository;
import com.greking.Greking.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final PasswordResetTokenRepository tokenRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                           EmailService emailService, PasswordResetTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.tokenRepository = tokenRepository;
    }

    @Override
    @Transactional
    public User registerUser(User user) throws Exception {
        validateUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    private void validateUser(User user) throws Exception {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("Email already exists.");
        }
        if (!user.isTermsOfServiceAccepted() || !user.isPrivacyPolicyAccepted()) {
            throw new Exception("Terms of Service and Privacy Policy must be accepted.");
        }
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }


    @Override
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



//    @Override
//    @Transactional(readOnly = false)
//    public void createPasswordResetTokenForUser(String email) {
//        Optional<User> userOptional = userRepository.findByEmail(email);
//        if (userOptional.isEmpty()) {
//            System.out.println("Email not found: " + email);
//            throw new IllegalArgumentException("Email not found.");
//        }
//        User user = userOptional.get();
//
//
//        // 기존 토큰 삭제
//        tokenRepository.deleteByUser(user);
//        System.out.println("Existing token deleted for user: " + user.getEmail());
//
//        String token = UUID.randomUUID().toString();
//        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(15);
//
//        PasswordResetToken resetToken = new PasswordResetToken();
//        resetToken.setToken(token);
//        resetToken.setExpiryDate(expiryDate);
//        resetToken.setUser(user);
//
//        System.out.println("Reset token before saving: " + resetToken);
//
//        // 저장 로직
//        PasswordResetToken savedToken = tokenRepository.save(resetToken);
//        System.out.println("Generated token for user " + user.getEmail() + ": " + token);
//        System.out.println("Saved token: " + savedToken);
//
//        String subject = "Password Reset Request";
//        String text = "Your password reset token is: " + token;
//        emailService.sendEmail(user.getEmail(), subject, text);
//    }


    @Override
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

    @Override
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

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            tokenRepository.deleteByUser(user); // 관련된 비밀번호 재설정 토큰 삭제
            userRepository.deleteById(userId); // 사용자 삭제
            System.out.println("User with ID " + userId + " deleted.");
        } else {
            throw new IllegalArgumentException("User not found.");
        }
    }
}
