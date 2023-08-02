package youtube.mapper.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import youtube.domain.member.vo.*;

import java.time.LocalDate;

import static youtube.global.constant.ValidExceptionMessageConstant.*;

@Builder
public record MemberSignupRequest(
        @NotNull(message = NICKNAME_NULL)
        Nickname nickname,
        @NotNull(message = LOGIN_ID_NULL)
        LoginId loginId,
        @NotNull(message = PASSWORD_NULL)
        Password password,
        Gender gender,
        LocalDate birthDate
) {
}
