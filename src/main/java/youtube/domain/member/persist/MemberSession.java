package youtube.domain.member.persist;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youtube.domain.member.vo.*;

import static youtube.global.constant.SessionConstant.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSession {

    private long id;

    private Nickname nickname;

    private RoleType roleType;

    private long likedVideosCount;

    private long watchLaterVideosCount;

    @Builder
    private MemberSession(final long id, final Nickname nickname, final RoleType roleType,
                          final long likedVideosCount, final long watchLaterVideosCount) {
        this.id = id;
        this.nickname = nickname;
        this.roleType = roleType;
        this.likedVideosCount = likedVideosCount;
        this.watchLaterVideosCount = watchLaterVideosCount;
    }

    public void makeSession(final HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(MEMBER_SESSION.value, this);
    }
}
