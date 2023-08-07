package youtube.mapper.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import youtube.domain.member.persist.Member;
import youtube.domain.member.persist.MemberSession;
import youtube.domain.member.vo.Gender;
import youtube.domain.member.vo.RoleType;
import youtube.mapper.member.dto.MemberDetailedResponse;
import youtube.mapper.member.dto.MemberResponse;
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

    public static MemberSession toMemberSession(final Member member) {
        return MemberSession.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .roleType(member.getRoleType())
                .likedVideosCount(member.getLikedVideosCount())
                .watchLaterVideosCount(member.getWatchLaterVideosCount())
                .build();
    }

    public static MemberResponse toMemberResponse(final MemberSession memberSession) {
        return MemberResponse.builder()
                .id(memberSession.getId())
                .nickname(memberSession.getNickname().getValue())
                .roleType(memberSession.getRoleType())
                .likedVideosCount(memberSession.getLikedVideosCount())
                .watchLaterVideosCount(memberSession.getWatchLaterVideosCount())
                .build();
    }

    public static MemberDetailedResponse toMemberDetailedResponse(final Member member) {
        return MemberDetailedResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname().getValue())
                .loginId(member.getLoginId().getValue())
                .roleType(member.getRoleType())
                .gender(member.getGender())
                .birthDate(member.getBirthDate())
                .likedVideosCount(member.getLikedVideosCount())
                .watchLaterVideosCount(member.getWatchLaterVideosCount())
                .build();
    }
}
