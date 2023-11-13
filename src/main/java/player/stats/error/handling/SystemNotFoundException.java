package player.stats.error.handling;

public class SystemNotFoundException extends RuntimeException {
    public SystemNotFoundException(String system) {
            super("400: A required parameter was not supplied or is invalid");
    }

    public SystemNotFoundException() {
            super("400: A required parameter was not supplied or is invalid, or system or name does not match the existing metric");
    }

}
