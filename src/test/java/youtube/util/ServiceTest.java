package youtube.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import youtube.application.channel.query.QueryChannelCacheById;
import youtube.application.video.video_info.query.QueryVideoInfoCacheById;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.domain.member.vo.Password;
import youtube.repository.channel.ChannelRepository;
import youtube.repository.jwt.JwtRepository;
import youtube.repository.member.MemberRepository;
import youtube.repository.subscription.SubscriptionRepository;
import youtube.repository.video.video_info.VideoInfoRepository;
import youtube.repository.video.video_reaction.VideoReactionRepository;

import static youtube.util.TestConstant.*;

@AcceptanceTest
public class ServiceTest {

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
    protected QueryChannelCacheById queryChannelCacheById;

    @Autowired
    protected QueryVideoInfoCacheById queryVideoInfoCacheById;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected CacheManager cacheManager;

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