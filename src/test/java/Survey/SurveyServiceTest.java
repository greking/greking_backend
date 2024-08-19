package Survey;

import com.greking.Greking.Common.AppConfig;
import com.greking.Greking.Survey.service.SurveyService;
import com.greking.Greking.Survey.domain.SurveyResult;
import com.greking.Greking.Survey.domain.FitnessLevel;
import com.greking.Greking.User.domain.Grade;
import com.greking.Greking.User.domain.User;
import com.greking.Greking.User.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SurveyServiceTest {

    @InjectMocks
    private SurveyService surveyService;

    @Mock
    private UserRepository userRepository;

    private User user;
    private SurveyResult surveyResult;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);

        AppConfig appConfig = new AppConfig();
        surveyService = appConfig.surveyService(userRepository);

        // 초기 User 객체 설정
        user = new User();
        user.setUserid(1L); // 임의의 ID 설정
        user.setEmail("testtest@example.com");
        user.setPassword("password1");
        user.setNickname("testuser1");
        user.setTermsOfServiceAccepted(true);
        user.setPrivacyPolicyAccepted(true);
        user.setGrade(new Grade());  // Grade 객체 초기화
    
        
        // 초기 SurveyResult 객체 설정
        surveyResult = new SurveyResult();
        surveyResult.setQuestionAnswer1(2);
        surveyResult.setQuestionAnswer2(3);
        surveyResult.setQuestionAnswer3(2);
        surveyResult.setQuestionAnswer4(5);
        surveyResult.setQuestionAnswer5(1);
    }

    @Test
    public void testCalculateFitnessLevel() {
        // Given
        int score = 12;

        // When
        FitnessLevel level = surveyService.calculrateLevel(score);

        // Then
        assertEquals(FitnessLevel.LEVEL3, level); // 예상되는 레벨 검증
    }

    @Test
    public void testAllProcess() {
        // Given
        when(userRepository.findById(user.getUserid())).thenReturn(Optional.of(user));  // User 객체 모킹
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));  // 저장된 객체 반환 모킹

        // When
        /*surveyService.registerUserlevel(user.getUserid(), surveyResult);*/

        // Then
        assertEquals(FitnessLevel.LEVEL3, user.getFitnessLevel());  // 피트니스 레벨 검증
        verify(userRepository, times(1)).save(user);  // save 메서드가 한 번 호출되었는지 확인
    }
}
