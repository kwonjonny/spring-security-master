package spring.security.master.member.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.security.master.exception.handler.APIResponseDTO;
import spring.security.master.exception.handler.StatusEnums;
import spring.security.master.member.security.serivce.CustomUserDetails;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Log4j2
@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    private static final String SUCCESS_MESSAGE = "로그인 성공: 세션 및 토큰 정보를 저장 합니다.";
    private static final String DATE_FORMAT = "yyyy/MM/dd일 HH시:mm분";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);

    /**
     * 회원 시스템: 로그인 [성공]
     * <p>
     * 회원 시스템 로그인 성공을 수행합니다.
     * </p>
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param authentication 자격 증명
     * @throws IOException IOException 예외
     */
    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.info("CustomLoginSuccessHandler");
        final CustomUserDetails member = (CustomUserDetails) authentication.getPrincipal();
        final Map<String, Object> claims = Map.of("user", member.getAttributes());

        final APIResponseDTO<Map<String, Object>> successResponse = new APIResponseDTO<>(
                null,
                Map.of("user", member.getAttributes()),
                SUCCESS_MESSAGE,
                HttpServletResponse.SC_OK,
                StatusEnums.OK,
                simpleDateFormat.format(new Date())
        );
        final String responseBody = objectMapper.writeValueAsString(successResponse);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }
}