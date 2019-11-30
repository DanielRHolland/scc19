import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeanHelloWorld {

    @Bean
    public MyService myService() {
        return new MyServiceImpl();
    }
}
