package youtube.mapper.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import youtube.domain.member.vo.*;

import java.time.LocalDate;

@Builder
public record MemberDetailedResponse(
        long id,
        String nickname,
        String loginId,
        RoleType roleType,
        Gender gender,
        LocalDate birthDate,
        long likedVideosCount,
        long watchLaterVideosCount
) {
    @QueryProjection
    public MemberDetailedResponse {
    }
}
