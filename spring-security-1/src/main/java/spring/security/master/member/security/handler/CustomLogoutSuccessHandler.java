package spring.security.master.member.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import spring.security.master.exception.handler.APIResponseDTO;
import spring.security.master.exception.handler.StatusEnums;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Log4j2
@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final ObjectMapper objectMapper;

    private static final String SUCCESS_MESSAGE = "로그아웃 성공: 세션 및 인증 정보를 삭제합니다.";
    private static final String DATE_FORMAT = "yyyy/MM/dd일 HH시:mm분";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);

    @Override
    public void onLogoutSuccess(@NotNull final HttpServletRequest request,
                                @NotNull final HttpServletResponse response,
                                final Authentication authentication) throws IOException, ServletException {
        log.info("CustomLogoutSuccessHandler");

        final APIResponseDTO<String> successResponse = new APIResponseDTO<>(
                null,
                APIResponseDTO.NOT_AVAILABLE_DATA,
                SUCCESS_MESSAGE,
                HttpServletResponse.SC_OK,
                StatusEnums.OK,
                simpleDateFormat.format(new Date()));
        final String responseBody = objectMapper.writeValueAsString(successResponse);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }
}
