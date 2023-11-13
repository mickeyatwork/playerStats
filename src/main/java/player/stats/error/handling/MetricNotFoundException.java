package player.stats.error.handling;

public class MetricNotFoundException extends RuntimeException {

    public MetricNotFoundException(Long id) {
        super("404: The specified metric was not found");
    }

    public MetricNotFoundException(String name) {
        super("404: The specified metric was not found");
    }

    public MetricNotFoundException() {
        super("400: A required parameter was not supplied or is invalid");
    }

}

