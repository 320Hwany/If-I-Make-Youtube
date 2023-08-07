package youtube.domain.member.persist;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youtube.domain.member.vo.*;


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
}
