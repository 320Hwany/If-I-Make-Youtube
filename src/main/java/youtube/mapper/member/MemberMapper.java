package youtube.mapper.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.Gender;
import youtube.domain.member.vo.RoleType;
import youtube.mapper.member.dto.MemberSignupRequest;

import java.time.LocalDate;

public class MemberMapper {

    private MemberMapper() {
    }

    // 회원가입시 값을 입력하지 않은 필드는 기본값을 넣는다
    // roleType : NORMAL, gender : UNKNOWN, birthDate : 0000-01-01
    public static Member toEntity(final MemberSignupRequest dto, final PasswordEncoder passwordEncoder) {
        return Member.builder()
                .nickname(dto.nickname())
                .loginId(dto.loginId())
                .password(dto.password().encode(passwordEncoder))
                .roleType(RoleType.NORMAL)
                .gender((dto.gender() != null) ? dto.gender() : Gender.UNKNOWN)
                .birthDate((dto.birthDate() != null) ? dto.birthDate() :
                        LocalDate.of(0,1,4))
                .build();
    }
}
