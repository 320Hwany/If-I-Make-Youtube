package youtube.mapper.member;

import youtube.domain.member.persist.Member;
import youtube.mapper.member.dto.MemberSignupRequest;

public class MemberMapper {

    public static Member toEntity(MemberSignupRequest dto) {
        return Member.builder()
                .nickname(dto.nickname())
                .loginId(dto.loginId())
                .password(dto.password())
                .roleType(dto.roleType())
                .gender(dto.gender())
                .birthDate(dto.birthDate())
                .build();
    }
}
