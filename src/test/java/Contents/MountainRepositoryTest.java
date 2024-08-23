package Contents;

import com.greking.Greking.Contents.domain.Mountain;
import com.greking.Greking.Contents.repository.MountainRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MountainRepositoryTest {

    @Mock
    private MountainRepository mountainRepository;


    @Test
    public void testSaveAndFindMountain() {
        // Given: 새로운 Mountain 엔티티 생성
        Mountain mountain = Mountain.builder()
                .name("설악산")
                .latitude(38.1194)
                .longitude(128.4656)
                .build();

        // When: Mountain 엔티티를 저장
        Mountain savedMountain = mountainRepository.save(mountain);

        // Then: 저장된 엔티티를 조회하여 검증
        Mountain foundMountain = mountainRepository.findById(savedMountain.getId()).orElse(null);
        assertThat(foundMountain).isNotNull();
        assertThat(foundMountain.getName()).isEqualTo("설악산");
        assertThat(foundMountain.getLatitude()).isEqualTo(38.1194);
        assertThat(foundMountain.getLongitude()).isEqualTo(128.4656);
    }
}
