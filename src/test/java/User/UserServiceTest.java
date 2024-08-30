package User;

import com.greking.Greking.Common.AppConfig;
import com.greking.Greking.Contents.repository.CourseRepository;
import com.greking.Greking.User.domain.User;
import com.greking.Greking.User.repository.PasswordResetTokenRepository;
import com.greking.Greking.User.repository.UserCourseRepository;
import com.greking.Greking.User.repository.UserRepository;
import com.greking.Greking.User.service.EmailService;
import com.greking.Greking.User.service.PasswordResetService;
import com.greking.Greking.User.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    EmailService emailService;
    PasswordResetTokenRepository tokenRepository;
    PasswordResetService passwordResetService;
    UserCourseRepository userCourseRepository;
    CourseRepository courseRepository;
    UserService userService;
    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;
    User user;

    @BeforeEach
    public void beforeEach() {
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        emailService = Mockito.mock(EmailService.class);
        passwordResetService = Mockito.mock(PasswordResetService.class);
        tokenRepository = Mockito.mock(PasswordResetTokenRepository.class);

        AppConfig appConfig = new AppConfig();

        user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setNickname("testuser");
        user.setTermsOfServiceAccepted(true);
        user.setPrivacyPolicyAccepted(true);
    }

    @Test
    public void testRegisterUser() throws Exception {
        // given
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // when
        User registeredUser = userService.registerUser(user);

        // then
        assertThat(registeredUser).isNotNull();
        assertThat(registeredUser.getPassword()).isEqualTo("hashedPassword");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testFindUserByEmail() {
        // given
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // when
        User foundUser = passwordResetService.findUserByEmail(user.getEmail());

        // then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void testDeleteUser() {
        // given
        Long userId = user.getUserid();

        // when
        userService.deleteUser(userId);

        // then
        verify(userRepository, times(1)).deleteById(userId);
    }
}
