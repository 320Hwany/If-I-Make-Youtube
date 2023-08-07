package youtube.global.argument_resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import youtube.domain.member.persist.MemberSession;
import youtube.global.exception.ExceptionForLog;
import youtube.global.exception.UnAuthorizedException;

import static youtube.global.constant.ExceptionMessageForLog.HTTP_SERVLET_REQUEST_NOTFOUND;
import static youtube.global.constant.SessionConstant.MEMBER_SESSION;

public class MemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasMemberSessionType = parameter.getParameterType().equals(MemberSession.class);
        boolean hasLoginMemberAnnotation = parameter.hasParameterAnnotation(Login.class);
        return hasMemberSessionType && hasLoginMemberAnnotation;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        assert request != null;
        return request.getAttribute(MEMBER_SESSION.value);
    }
}
