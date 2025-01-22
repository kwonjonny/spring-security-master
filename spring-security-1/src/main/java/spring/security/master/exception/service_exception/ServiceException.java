package spring.security.master.exception.service_exception;

import lombok.Getter;
import spring.security.master.exception.error_interface.IErrorCode;

@Getter
public class ServiceException extends RuntimeException {
    private final IErrorCode errorCode;

    public ServiceException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ServiceException(final String message, final IErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
