package youtube.domain.member.persist;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static youtube.global.constant.SessionConstant.MEMBER_SESSION;

class MemberSessionTest {

    @Test
    @DisplayName("HttpServletRequest에 MemberSession으로 세션을 생성합니다")
    void makeSession() {
        // given
        MemberSession memberSession = new MemberSession();
        MockHttpServletRequest request = new MockHttpServletRequest();

        // when
        memberSession.makeSession(request);
        HttpSession session = request.getSession(false);

        // then
        assertThat(session).isNotNull();
        MemberSession sessionAttribute = (MemberSession) session.getAttribute(MEMBER_SESSION.value);
        assertThat(sessionAttribute).isNotNull();
    }
}