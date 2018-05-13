package reactive.spring.webflux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Dedicated 'blocking' service-based layer.
 */
@Service
public class ApplicationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationService.class);

    /**
     * Adds two values.
     *
     * @param firstValue  - represents first value.
     * @param secondValue - represents second value.
     * @return the sum of two numbers.
     */
    public double add(double firstValue, double secondValue) {
        return firstValue + secondValue;
    }

    /**
     * Subtracts second value from fist value.
     *
     * @param firstValue  - represents first value.
     * @param secondValue - represents second value.
     * @return the sum of two numbers.
     */
    public double subtract(double firstValue, double secondValue) {
        return firstValue - secondValue;
    }

    /**
     * Multiplies first value by second one.
     *
     * @param firstValue - represents first value.
     * @param secondValue - represents second value.
     * @return secondValue sum of two numbers.
     */
    public double multiply(double firstValue, double secondValue) {
        return firstValue * secondValue;
    }

    /**
     * Divides first value by second one.
     *
     * @param firstValue - represents first value.
     * @param secondValue - represents second value.
     * @return secondValue sum of two numbers.
     */
    public double divide(double firstValue, double secondValue) {
        if (secondValue == 0) {
            LOGGER.warn("Dividing by zero.");
        }
        return firstValue / secondValue;
    }

    public double customOperation(double firstValue, double secondValue) {
        return Math.sqrt(secondValue);
    }

}
