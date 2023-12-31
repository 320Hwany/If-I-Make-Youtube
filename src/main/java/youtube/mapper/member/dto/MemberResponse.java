package youtube.mapper.member.dto;

import lombok.Builder;
import youtube.domain.member.vo.RoleType;


@Builder
public record MemberResponse(
        long id,
        String nickname,
        RoleType roleType,
        long likedVideosCount,
        long watchLaterVideosCount
) {
}
