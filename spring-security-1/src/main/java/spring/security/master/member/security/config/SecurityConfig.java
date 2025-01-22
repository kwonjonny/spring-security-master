package spring.security.master.member.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import spring.security.master.member.security.handler.*;
import spring.security.master.member.security.serivce.CustomUserDetailsService;

@Log4j2
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String LOGIN_API_PATH = "/api/management/member/login";
    private static final String LOGOUT_API_PATH = "/api/management/member/logout";
    private static final String USER_NAME_PARAMETER = "id";
    private static final String USER_PASSWORD_PARAMETER = "password";

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomLoginSuccessHandler customLoginSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        try {
            return httpSecurity
                    .csrf(AbstractHttpConfigurer::disable)
                    .httpBasic(AbstractHttpConfigurer::disable)
                    .formLogin(security -> {
                        security.loginProcessingUrl(LOGIN_API_PATH);
                        security.usernameParameter(USER_NAME_PARAMETER);
                        security.passwordParameter(USER_PASSWORD_PARAMETER);
                        security.successHandler(customLoginSuccessHandler);
                        security.failureHandler(customAuthenticationFailureHandler);
                    })
                    .logout(security -> {
                        security.logoutUrl(LOGOUT_API_PATH);
                        security.invalidateHttpSession(true);
                        security.clearAuthentication(true);
                        security.logoutSuccessHandler(customLogoutSuccessHandler);
                    })
                    .exceptionHandling(exception -> {
                        exception.accessDeniedHandler(customAccessDeniedHandler);
                        exception.authenticationEntryPoint(customAuthenticationEntryPoint);
                    })
                    .userDetailsService(customUserDetailsService)
                    .headers(headers -> {
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
                    })
                    .build();
        } catch (Exception e) {
            log.error("Spring Security Config Error", e);
            throw new RuntimeException(e);
        }
    }
}