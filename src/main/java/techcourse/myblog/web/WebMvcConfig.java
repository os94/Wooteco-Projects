package techcourse.myblog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import techcourse.myblog.web.interceptor.LoginInterceptor;
import techcourse.myblog.web.interceptor.UserAuthInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;
    private final UserAuthInterceptor userAuthInterceptor;

    @Autowired
    public WebMvcConfig(LoginInterceptor loginInterceptor, UserAuthInterceptor userAuthInterceptor) {
        this.loginInterceptor = loginInterceptor;
        this.userAuthInterceptor = userAuthInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/")
                .excludePathPatterns("/login")
                .excludePathPatterns("/signup")
                .excludePathPatterns("/users")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/images/**");

        registry.addInterceptor(userAuthInterceptor)
                .addPathPatterns("/user/update/{pageId}")
                .addPathPatterns("/user/delete/{pageId}");
    }
}
