package techcourse.myblog.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import techcourse.myblog.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static techcourse.myblog.web.SessionManager.USER;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<User> userSession = Optional.ofNullable((User) request.getSession().getAttribute(USER));
        log.debug("PATH : {}", request.getRequestURI());
        log.debug("METHOD : {}", request.getMethod());
        log.debug("LOGIN : {}", userSession.isPresent());

        if (userSession.isPresent()) {
            return true;
        }
        response.sendRedirect("/login");
        return false;
    }
}
