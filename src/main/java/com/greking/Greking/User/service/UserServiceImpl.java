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
    private final PasswordResetTokenRepository tokenRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                            PasswordResetTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    @Transactional
    public User registerUser(User user) throws Exception {
        validateUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
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


    //회원 있는지 유효성 검증
    private void validateUser(User user) throws Exception {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("Email already exists.");
        }
        if (!user.isTermsOfServiceAccepted() || !user.isPrivacyPolicyAccepted()) {
            throw new Exception("Terms of Service and Privacy Policy must be accepted.");
        }
    }
}
