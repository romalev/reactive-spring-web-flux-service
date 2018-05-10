package reactive.spring.webflux.dto;

import java.util.Objects;

/**
 * Represents application response that goes outside.
 */
public class ApplicationResponse {

    private final String result;
    private final String message;

    public ApplicationResponse(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApplicationResponse)) return false;
        ApplicationResponse that = (ApplicationResponse) o;
        return Objects.equals(getResult(), that.getResult()) &&
                Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getResult(), getMessage());
    }

    @Override
    public String toString() {
        return "ApplicationResponse{" +
                "result=" + result +
                ", message='" + message + '\'' +
                '}';
    }
}
