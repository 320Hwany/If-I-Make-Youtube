package youtube.mapper.member.dto;

import jakarta.validation.constraints.NotNull;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Password;

import static youtube.global.constant.AnnotationMessageConstant.LOGIN_ID_NULL;
import static youtube.global.constant.AnnotationMessageConstant.PASSWORD_NULL;

public record MemberLoginRequest(
        @NotNull(message = LOGIN_ID_NULL)
        LoginId loginId,
        @NotNull(message = PASSWORD_NULL)
        Password password
) {
}
