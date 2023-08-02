package youtube.mapper.member.dto;

import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Password;

public record MemberLoginRequest(
        LoginId loginId,
        Password password
) {
}
