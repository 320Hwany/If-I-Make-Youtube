package youtube.mapper.member.dto;

import lombok.Builder;
import youtube.domain.member.vo.*;

import java.time.LocalDate;

@Builder
public record MemberResponse(
        long id,
        String nickname,
        String loginId,
        RoleType roleType,
        Gender gender,
        LocalDate birthDate,
        long likedVideosCount,
        long watchLaterVideosCount
) {
}
