package spring.security.master.member.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
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
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;
    private static final String ERROR_MESSAGE = "로그인 실패: 자격 증명에 실패하였습니다.";
    private static final String DATE_FORMAT = "yyyy/MM/dd일 HH시:mm분";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);

    /**
     * 로그인 실패시 작동
     * <p>
     * 이 핸들러는 로그인 실패 시 작동합니다.
     * </p>
     * 예를 들어 사용자가 잘못된 아이디나 비밀번호를 입력하여 로그인에 실패할 경우
     * <p>
     * HTTP 401 Unauthorized 상태와 로그인 실패 메시지를 JSON 형식으로 반환합니다.
     * </p>
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param exception 인증 예외
     * @throws IOException IOException 예외
     */
    @Override
    @Transactional(readOnly = true)
    public void onAuthenticationFailure(@NotNull final HttpServletRequest request,
                                        @NotNull final HttpServletResponse response,
                                        @NotNull final AuthenticationException exception) throws IOException {
        log.info("CustomAuthenticationFailureHandler");
        final APIResponseDTO<String> errorResponse = new APIResponseDTO<>(
                null,
                APIResponseDTO.NOT_AVAILABLE_DATA,
                ERROR_MESSAGE,
                HttpServletResponse.SC_UNAUTHORIZED,
                StatusEnums.FAIL,
                simpleDateFormat.format(new Date()));
        final String responseBody = objectMapper.writeValueAsString(errorResponse);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }
}
