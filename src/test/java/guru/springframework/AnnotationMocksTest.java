package guru.springframework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnnotationMocksTest {
    @Mock
    Map<Long, String> mapMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // false : this.getClass()
        System.out.println("Mockito initialized...");

    }

    @Test
    void testAnnotationMock() {
        mapMock.put(1l, "Khayal");
    }
}
