package Contents;

import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.Contents.dto.CourseDto;
import com.greking.Greking.Contents.repository.CourseRepository;
import com.greking.Greking.Contents.service.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CourseServiceTest {

    @InjectMocks
    private CourseServiceImpl courseService;

    @Mock
    private CourseRepository courseRepository;

    private Course course;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Mockito 어노테이션 초기화

        course = new Course();
        course.setId(1L);
        course.setCourseName("Test Course");
        course.setDifficulty("Medium");
        course.setDistance(10);
        course.setAscendTime(3);
        course.setDescendTime(2);
    }

    @Test
    public void testGetCourseById() {
        // Given
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));

        // When
        CourseDto result = courseService.getCourseById(course.getId());

        // Then
        assertEquals(course.getCourseName(), result.getCourseName());
        verify(courseRepository, times(1)).findById(course.getId());
    }

    @Test
    public void testSaveCourse() throws Exception {
        // Given
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // When
        Course result = courseService.saveCourse(course);

        // Then
        assertEquals(course.getCourseName(), result.getCourseName());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void testDeleteCourse() {
        // Given
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));

        // When
        courseService.deleteCourse(course.getId());

        // Then
        verify(courseRepository, times(1)).deleteById(course.getId());
    }
}
