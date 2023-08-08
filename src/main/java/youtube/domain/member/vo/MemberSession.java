package youtube.domain.member.vo;

import lombok.Builder;

@Builder
public record MemberSession(
        long id,
        Nickname nickname,
        RoleType roleType,
        long likedVideosCount,
        long watchLaterVideosCount
) {
}
