package spring.security.master.member.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.security.master.exception.handler.APIResponseDTO;
import spring.security.master.exception.handler.StatusEnums;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Log4j2
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    private static final String ERROR_MESSAGE = "인가 불가: 접근이 거부 되었습니다.";
    private static final String DATE_FORMAT = "yyyy/MM/dd일 HH시:mm분";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);

    /**
     * 인증되지 않는 사용자가 보호된 리소스에 접근할 때 작동
     * <p>
     * 이 핸들러는 인증되지 않는 사용자가 보호된 리소스에 접근할 때 작동됩니다.
     * </p>
     * 예를 들어 인증되지 않는 사용자가 API 엔트리 포인트를 접근하면 HTTP 401 Unauthorized 응답을 반환합니다.
     * <p>
     * 핸들러를 통해 JSON 형태의 에러 응답을 커스텀하여 반환합니다.
     * </p>
     *
     * @param request       HttpServletRequest
     * @param response      HttpServletResponse
     * @param authException 인증 실패 예외
     * @throws IOException IOException 예외
     */
    @Override
    @Transactional(readOnly = true)
    public void commence(@NotNull final HttpServletRequest request,
                         @NotNull final HttpServletResponse response,
                         @NotNull final AuthenticationException authException) throws IOException {
        log.info("CustomAuthenticationEntryPoint");
        final APIResponseDTO<String> errorResponse = new APIResponseDTO<>(
                null,
                APIResponseDTO.NOT_AVAILABLE_DATA,
                ERROR_MESSAGE,
                HttpServletResponse.SC_UNAUTHORIZED,
                StatusEnums.FAIL,
                simpleDateFormat.format(new Date()));
        final String responseBody = objectMapper.writeValueAsString(errorResponse);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }
}