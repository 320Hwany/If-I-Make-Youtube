package youtube.application.comment.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.MemberSession;
import youtube.domain.member.vo.Nickname;
import youtube.domain.member.vo.Password;
import youtube.domain.video.video_info.persist.VideoInfo;
import youtube.global.constant.NumberConstant;
import youtube.mapper.comment.dto.CommentSaveRequest;
import youtube.util.ServiceTest;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;
import static youtube.global.constant.NumberConstant.*;
import static youtube.util.TestConstant.*;

class CommentCreatorTest extends ServiceTest {

    @Autowired
    protected CommentCreator commentCreator;

    @Test
    @DisplayName("로그인 한 회원이 동영상에 댓글을 작성합니다")
    void commandCommentSave() {
        // given 1
        VideoInfo videoInfo = VideoInfo.builder()
                .commentCount(ZERO.value)
                .build();

        videoInfoRepository.save(videoInfo);

        // given 2
        Member member = Member.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .loginId(LoginId.from(TEST_LOGIN_ID.value))
                .password(Password.from(TEST_PASSWORD.value))
                .build();

        memberRepository.save(member);

        CommentSaveRequest dto = CommentSaveRequest.builder()
                .videoInfoId(videoInfo.getId())
                .content("댓글 내용")
                .build();

        // given 3
        MemberSession memberSession = MemberSession.builder()
                .id(member.getId())
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .build();

        // when
        commentCreator.command(memberSession, dto);

        // then
        assertThat(commentRepository.count()).isEqualTo(new AtomicInteger(1).get());
    }
}