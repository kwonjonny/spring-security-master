package spring.security.master.exception.error_interface;

import org.springframework.http.HttpStatus;

public interface IErrorCode {
    String getCode();
    String getMessage();
    HttpStatus getHttpStatus();
}
