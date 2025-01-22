package spring.security.master.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponseDTO<T> {
    public static final String NOT_AVAILABLE_DATA = "Not Available Data";
    public static final String REQUEST_UPDATE_SUCCESS = "요청이 성공적으로 업데이트 되었습니다.";
    public static final String REQUEST_DELETE_SUCCESS = "요청이 성공적으로 삭제되었습니다.";
    public static final String REQUEST_SUCCESS = "요청이 성공적으로 처리되었습니다.";
    public static final String REQUEST_CREATE_SUCCESS = "요청이 성공적으로 생성 되었습니다.";
    private static final String DATE_FORMAT = "yyyy/MM/dd일 HH시:mm분";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);

    private T response;
    private T data;
    private String message;
    private Integer status;
    private StatusEnums result;
    private String timeStamp;

    @Builder
    public APIResponseDTO(T response, T data, String message, Integer status, StatusEnums result, String timeStamp) {
        this.response = response;
        this.data = data;
        this.message = message;
        this.status = status;
        this.result = result;
        this.timeStamp = timeStamp;
    }

    public static <T> @NotNull ResponseEntity<APIResponseDTO<T>> ofSuccessResponse(@NotNull final T data,
                                                                                   @NotNull final HttpStatus status) {
        final APIResponseDTO<T> response = APIResponseDTO.<T>builder()
                .timeStamp(simpleDateFormat.format(new Date()))
                .result(StatusEnums.OK)
                .status(status.value())
                .message(REQUEST_SUCCESS)
                .data(data)
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    public static <T> @NotNull ResponseEntity<APIResponseDTO<T>> ofSuccessCreateResponse(@NotNull final HttpStatus status) {
        final APIResponseDTO<T> response = APIResponseDTO.<T>builder()
                .timeStamp(simpleDateFormat.format(new Date()))
                .result(StatusEnums.OK)
                .status(status.value())
                .message(REQUEST_CREATE_SUCCESS)
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    public static <T> @NotNull ResponseEntity<APIResponseDTO<T>> ofSuccessUpdateResponse(@NotNull final HttpStatus status) {
        final APIResponseDTO<T> response = APIResponseDTO.<T>builder()
                .timeStamp(simpleDateFormat.format(new Date()))
                .result(StatusEnums.OK)
                .status(status.value())
                .message(REQUEST_UPDATE_SUCCESS)
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    public static <T> @NotNull ResponseEntity<APIResponseDTO<T>> ofSuccessDeleteResponse(@NotNull final HttpStatus status) {
        final APIResponseDTO<T> response = APIResponseDTO.<T>builder()
                .timeStamp(simpleDateFormat.format(new Date()))
                .result(StatusEnums.OK)
                .status(status.value())
                .message(REQUEST_DELETE_SUCCESS)
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    public static <T> @NotNull ResponseEntity<APIResponseDTO<T>> ofErrorResponse(@NotNull final T data,
                                                                                 @NotNull final HttpStatus status,
                                                                                 @NotNull final String message) {
        final APIResponseDTO<T> response = APIResponseDTO.<T>builder()
                .timeStamp(simpleDateFormat.format(new Date()))
                .result(StatusEnums.FAIL)
                .status(status.value())
                .message(message)
                .data(data)
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    public static <T> @NotNull ResponseEntity<APIResponseDTO<T>> ofErrorResponse(@NotNull final HttpStatus status,
                                                                                 @NotNull final String message) {
        final APIResponseDTO<T> response = APIResponseDTO.<T>builder()
                .timeStamp(simpleDateFormat.format(new Date()))
                .result(StatusEnums.FAIL)
                .status(status.value())
                .message(message)
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }
}
