package youtube.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import youtube.domain.member.persist.MemberSession;
import youtube.global.exception.UnAuthorizedException;

import static youtube.global.constant.SessionConstant.*;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new UnAuthorizedException();
        }

        MemberSession memberSession = (MemberSession) session.getAttribute(MEMBER_SESSION.value);
        if (memberSession == null) {
            throw new UnAuthorizedException();
        }

        return true;
    }
}
