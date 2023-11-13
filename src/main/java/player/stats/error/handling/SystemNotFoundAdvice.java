package player.stats.error.handling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SystemNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(SystemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String systemNotFoundHandler(SystemNotFoundException ex) {
        return ex.getMessage();
    }
}
