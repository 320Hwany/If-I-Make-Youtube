package youtube.global.argument_resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;

import static youtube.global.constant.StringConstant.MEMBER_SESSION;

public class MemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        boolean hasMemberSessionType = parameter.getParameterType().equals(MemberSession.class);
        boolean hasLoginMemberAnnotation = parameter.hasParameterAnnotation(Login.class);
        return hasMemberSessionType && hasLoginMemberAnnotation;
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        assert request != null;
        return request.getAttribute(MEMBER_SESSION.value);
    }
}
