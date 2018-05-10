package reactive.spring.webflux.dto;

import java.util.Objects;

/**
 * Represents request that comes in to rest-service.
 */
public class ApplicationRequest {

    private final Double x;
    private final Double y;


    public ApplicationRequest(final Double x, final Double y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "ApplicationRequest{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApplicationRequest)) return false;
        ApplicationRequest that = (ApplicationRequest) o;
        return Objects.equals(getX(), that.getX()) &&
                Objects.equals(getY(), that.getY());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
