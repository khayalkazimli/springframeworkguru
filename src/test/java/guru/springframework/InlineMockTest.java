package guru.springframework;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class InlineMockTest {
    @Test
    void testInlineMock() {
        Map mapMock = mock(Map.class);
        assertEquals(0, mapMock.size());
    }
    @Test
    void testWithoutMock(){
        Map<?, ?> map = new HashMap<>();
        assertEquals(0, map.size());
    }
}
