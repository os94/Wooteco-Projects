package com.woowacourse.dsgram.web.interceptor;

import com.woowacourse.dsgram.service.dto.user.LoggedInUser;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class UnauthenticatedUserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().equals("/api/users") && request.getMethod().equals("POST")) {
            return true;
        }

        Optional<LoggedInUser> userSession = Optional.ofNullable((LoggedInUser) request.getSession().getAttribute(LoggedInUser.SESSION_USER));

        if (!userSession.isPresent()) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
