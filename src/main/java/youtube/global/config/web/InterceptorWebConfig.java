package youtube.global.config.web;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import youtube.global.interceptor.LoginInterceptor;

public class InterceptorWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/signup", "/api/login");
    }
}
