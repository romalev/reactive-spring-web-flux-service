package reactive.spring.webflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactive.spring.webflux.functions.*;

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

    @Bean
    public ApplicationFunction<Double, Double> getDivideApplicationFunction() {
        return new DivideApplicationFunction();
    }

    @Bean
    public ApplicationFunction<Double, Double> getMultiplyApplicationFunction() {
        return new MultiplyApplicationFunction();
    }

    @Bean
    public ApplicationFunction<Double, Double> getPowerOf2SubSqrtApplicationFunction() {
        return new PowerOf2SubSqrtApplicationFunction();
    }


}
