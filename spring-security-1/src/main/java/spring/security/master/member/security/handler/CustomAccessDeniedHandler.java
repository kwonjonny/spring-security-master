package spring.security.master.member.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class CustomAccessDeniedHandler implements AccessDeniedHandler {


    private final ObjectMapper objectMapper;
    private static final String ERROR_MESSAGE = "인가 불가: 접근이 거부 되었습니다.";
    private static final String DATE_FORMAT = "yyyy/MM/dd일 HH시:mm분";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);

    /**
     * 인증된 사용자가 권한이 부족하여 접근할 수 없는 리소스에 접근할때 작동됩니다.
     * </p>
     * 예를 들어, ROLE_USER 권한만 있는 사용자가 ROLE_ADMIN 권한이 필요한 관리자 페이지에 접근하려고 할 때
     * <p>
     * HTTP 403 Forbidden 상태와 접근 거부 메시지를 JSON 형태로 반환합니다.
     * </p>
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param accessDeniedException 인증 실패 예외
     * @throws IOException IOException 예외
     */
    @Override
    @Transactional(readOnly = true)
    public void handle(@NotNull final HttpServletRequest request,
                       @NotNull final HttpServletResponse response,
                       @NotNull final AccessDeniedException accessDeniedException) throws IOException {
        log.info("AccessDeniedHandler");
        final APIResponseDTO<String> errorResponse = new APIResponseDTO<>(
                null,
                APIResponseDTO.NOT_AVAILABLE_DATA,
                ERROR_MESSAGE,
                HttpServletResponse.SC_FORBIDDEN,
                StatusEnums.FAIL,
                simpleDateFormat.format(new Date()));
        final String responseBody = objectMapper.writeValueAsString(errorResponse);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }
}
