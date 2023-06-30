package com.venble.boot.security;

import com.venble.boot.common.exception.ErrorCode;
import com.venble.boot.common.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 未登录或token失效时访问接口时，自定义的返回结果
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        // 未登录或token失效时访问接口时，自定义的返回结果
        ServletUtils.writeJson(response, ErrorCode.UNAUTHORIZED);
    }
}
