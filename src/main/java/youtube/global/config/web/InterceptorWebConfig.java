package youtube.global.config.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import youtube.repository.jwt.JwtRepository;
import youtube.repository.member.MemberRepository;
import youtube.global.interceptor.LoginInterceptor;

@Configuration
public class InterceptorWebConfig implements WebMvcConfigurer {

    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final JwtRepository jwtRepository;

    public InterceptorWebConfig(final ObjectMapper objectMapper, final MemberRepository memberRepository,
                                final JwtRepository jwtRepository) {
        this.objectMapper = objectMapper;
        this.memberRepository = memberRepository;
        this.jwtRepository = jwtRepository;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(objectMapper, memberRepository, jwtRepository))
                .order(1)
                .addPathPatterns("/api/v2/**");
    }
}
