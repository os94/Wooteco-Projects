package com.woowacourse.dsgram.web.config;

import com.woowacourse.dsgram.web.argumentresolver.UserSessionArgumentResolver;
import com.woowacourse.dsgram.web.interceptor.AuthenticatedUserInterceptor;
import com.woowacourse.dsgram.web.interceptor.UnauthenticatedUserInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String ALL_PATTERN = "/**";

    @Value("${interceptor.unauthenticated}")
    private List<String> excludedUnauthenticatedPaths;

    @Value("${interceptor.authenticated}")
    private List<String> authenticatedPaths;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UnauthenticatedUserInterceptor())
                .addPathPatterns(ALL_PATTERN)
                .excludePathPatterns(excludedUnauthenticatedPaths);

        registry.addInterceptor(new AuthenticatedUserInterceptor())
                .addPathPatterns(authenticatedPaths);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserSessionArgumentResolver());
    }
}
