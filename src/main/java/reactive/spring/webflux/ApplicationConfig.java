package reactive.spring.webflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactive.spring.webflux.functions.AddApplicationFunction;
import reactive.spring.webflux.functions.ApplicationFunction;
import reactive.spring.webflux.functions.SubtractApplicationFunction;

/**
 * Contains application-specific configuration.
 */
@Configuration
public class ApplicationConfig {

    @Bean
    public ApplicationFunction<Double, Double> getAddApplicationFunction() {
        return new AddApplicationFunction();
    }

    @Bean
    public ApplicationFunction<Double, Double> getSubApplicationFunction() {
        return new SubtractApplicationFunction();
    }

}
