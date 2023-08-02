package youtube.mapper.member.dto;

import lombok.Builder;
import youtube.domain.member.vo.*;

import java.time.LocalDate;

@Builder
public record MemberSignupRequest(
        Nickname nickname,
        LoginId loginId,
        Password password,
        Gender gender,
        LocalDate birthDate
) {
}
