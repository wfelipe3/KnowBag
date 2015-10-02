package knowbag.ratpack;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by feliperojas on 8/30/15.
 */
@SpringBootApplication
public class SprintBootConfig {

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
