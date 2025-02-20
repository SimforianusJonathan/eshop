package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;

@SpringBootTest
class EshopApplicationTests {
    @Test
    void contextLoads() {}
    
    @Test
    void testMainMethodCallsSpringApplicationRun() {
        try (MockedStatic<SpringApplication> mockedStatic = mockStatic(SpringApplication.class)) {
            String[] arguments = {};
            EshopApplication.main(arguments);

            mockedStatic.verify(() -> SpringApplication.run(EshopApplication.class, arguments));
        }
    }
}
