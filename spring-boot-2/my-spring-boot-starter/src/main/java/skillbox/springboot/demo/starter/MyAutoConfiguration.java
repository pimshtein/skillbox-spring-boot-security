package skillbox.springboot.demo.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyAutoConfiguration {
    @Bean
    public MyService myService() {
        return new MyService();
    }
} 