package youtube.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import youtube.application.channel.ChannelCacheReader;
import youtube.application.video.video_info.VideoInfoCacheReader;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.domain.member.vo.Password;
import youtube.repository.channel.ChannelRepository;
import youtube.repository.comment.CommentRepository;
import youtube.repository.jwt.JwtRepository;
import youtube.repository.member.MemberRepository;
import youtube.repository.membership.MembershipRepository;
import youtube.repository.subscription.SubscriptionRepository;
import youtube.repository.video.video_info.VideoInfoRepository;
import youtube.repository.video.video_reaction.VideoReactionRepository;
import youtube.repository.video.video_watch_later.VideoWatchLaterRepository;
import youtube.repository.video.video_watched.VideoWatchedRepository;

import static youtube.util.TestConstant.*;

@SpringBootTest
public abstract class ServiceTest {

    @Autowired
    protected DatabaseCleaner databaseCleaner;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected ChannelRepository channelRepository;

    @Autowired
    protected JwtRepository jwtRepository;

    @Autowired
    protected SubscriptionRepository subscriptionRepository;

    @Autowired
    protected VideoInfoRepository videoInfoRepository;

    @Autowired
    protected VideoReactionRepository videoReactionRepository;

    @Autowired
    protected VideoWatchLaterRepository videoWatchLaterRepository;

    @Autowired
    protected VideoWatchedRepository videoWatchedRepository;

    @Autowired
    protected CommentRepository commentRepository;

    @Autowired
    protected ChannelCacheReader channelCacheReader;

    @Autowired
    protected VideoInfoCacheReader videoInfoCacheReader;

    @Autowired
    protected MembershipRepository membershipRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected CacheManager cacheManager;

    @BeforeEach
    void setUpDatabase() {
        databaseCleaner.cleanUp();
    }

    protected Member saveMember() {
        Password password = Password.from(TEST_PASSWORD.value);

        Member member = Member.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .loginId(LoginId.from(TEST_LOGIN_ID.value))
                .password(password.encode(passwordEncoder))
                .build();

        memberRepository.save(member);

        return member;
    }
}
