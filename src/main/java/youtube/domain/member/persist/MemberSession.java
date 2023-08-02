package youtube.domain.member.persist;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youtube.domain.member.vo.*;

import java.time.LocalDate;

import static youtube.global.constant.SessionConstant.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSession {

    private long id;

    private Nickname nickname;

    private LoginId loginId;

    private Password password;

    private RoleType roleType;

    private Gender gender;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthDate;

    private long likedVideosCount;

    private long watchLaterVideosCount;

    @Builder
    private MemberSession(final long id, final Nickname nickname, final LoginId loginId,
                          final Password password, final RoleType roleType, final Gender gender,
                          final LocalDate birthDate, final long likedVideosCount,
                          final long watchLaterVideosCount) {
        this.id = id;
        this.nickname = nickname;
        this.loginId = loginId;
        this.password = password;
        this.roleType = roleType;
        this.gender = gender;
        this.birthDate = birthDate;
        this.likedVideosCount = likedVideosCount;
        this.watchLaterVideosCount = watchLaterVideosCount;
    }

    public void makeSession(final HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(MEMBER_SESSION.value, this);
    }
}
