package player.stats.error.handling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MetricNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(MetricNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String metricNotFoundHandler(MetricNotFoundException ex) {
        return ex.getMessage();
    }
}

