package Contents;

import com.greking.Greking.Common.AppConfig;
import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.Contents.domain.Mountain;
import com.greking.Greking.Contents.repository.MountainRepository;
import com.greking.Greking.Contents.service.CourseService;
import com.greking.Greking.Contents.service.MountainServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MountainServiceTest {

    @InjectMocks
    private MountainServiceImpl mountainService;

    @Mock
    private MountainRepository mountainRepository;

    @Mock
    private CourseService courseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        AppConfig appConfig = new AppConfig();
        mountainService = new MountainServiceImpl(mountainRepository);
    }

    @Test
    public void testSaveMountain() {
        // Given: Mountain 객체 생성 및 초기화
        Mountain mountain = new Mountain();
        mountain.setId(1L);
        mountain.setName("설악산");
        mountain.setLatitude(38.1194);
        mountain.setLongitude(128.4656);

        // course 리스트를 초기화하고 값을 추가
        List<Course> courses = new ArrayList<>();
        Course course = new Course();
        course.setId(1L);
        course.setCourseName("Test Course");
        course.setMountain(mountain);
        courses.add(course);

        mountain.setCourse(courses);  // course 리스트를 Mountain 객체에 설정

        when(mountainRepository.save(any(Mountain.class))).thenReturn(mountain);

        // When
        Mountain savedMountain = mountainService.saveMountainWithCourse(mountain);

        // Then
        assertEquals("설악산", savedMountain.getName());
        assertEquals(38.1194, savedMountain.getLatitude());
        assertEquals(128.4656, savedMountain.getLongitude());
        assertEquals(1, savedMountain.getCourse().size());  // Course 리스트 크기 검증
        verify(mountainRepository, times(1)).save(mountain);
    }
}
