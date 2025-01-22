package spring.security.master.exception.handler;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import spring.security.master.exception.error_interface.IErrorCode;
import spring.security.master.exception.service_exception.ServiceException;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String HIGH_EXCEPTION_FORMAT = "예기치 않은 오류가 발생했습니다. 나중에 다시 시도해주세요.";
    private static final String MISSING_PARAM_EXCEPTION_FORMAT = "요청에 필요한 파라미터 '%s'가 누락되었습니다.";

    private static final String HIGH_EXCEPTION = "High Rank Exception";
    private static final String SERVICE_EXCEPTION = "Service Exception";
    private static final String DTO_EXCEPTION = "DTO Valid Exception";
    private static final String BINDING_EXCEPTION = "Bind Exception";
    private static final String PARAMETER_EXCEPTION = "Parameter Missing Exception";

    private <T> @NotNull ResponseEntity<APIResponseDTO<String>> createErrorResponse(final HttpStatus status, final String message, final String logMessage, final Exception ex) {
        logError(logMessage, ex);
        return APIResponseDTO.ofErrorResponse(APIResponseDTO.NOT_AVAILABLE_DATA, status, message);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<APIResponseDTO<String>> handleServiceException(final @NotNull ServiceException ex) {
        final IErrorCode iErrorCode = ex.getErrorCode();
        final String message = ex.getMessage();
        final HttpStatus httpStatus = iErrorCode.getHttpStatus();
        return createErrorResponse(httpStatus, message, SERVICE_EXCEPTION, ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponseDTO<String>> handleMethodArgumentNotValidException(final @NotNull MethodArgumentNotValidException ex) {
        final List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        final String message = String.join(", ", errors);
        return createErrorResponse(HttpStatus.BAD_REQUEST, message, DTO_EXCEPTION, ex);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<APIResponseDTO<String>> handleBindException(final @NotNull BindException ex) {
        final List<String> errors = ex.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        final String message = String.join(", ", errors);
        return createErrorResponse(HttpStatus.BAD_REQUEST, message, BINDING_EXCEPTION, ex);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<APIResponseDTO<String>> handleMissingParams(final @NotNull MissingServletRequestParameterException ex) {
        final String message = String.format(MISSING_PARAM_EXCEPTION_FORMAT, ex.getParameterName());
        return createErrorResponse(HttpStatus.BAD_REQUEST, message, PARAMETER_EXCEPTION, ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponseDTO<String>> handleException(final Exception ex) {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, HIGH_EXCEPTION_FORMAT, HIGH_EXCEPTION, ex);
    }

    private void logError(String message, @NotNull Exception ex) {
        log.error("{}: {}", message, ex.toString());
        log.error("Exception Stack Trace: ", ex);
    }
}
