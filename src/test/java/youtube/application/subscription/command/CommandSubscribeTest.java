package youtube.application.subscription.command;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import youtube.domain.channel.persist.Channel;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.domain.member.vo.Password;
import youtube.domain.subscription.Subscription;
import youtube.global.constant.CacheConstant;
import youtube.mapper.channel.ChannelMapper;
import youtube.repository.channel.ChannelRepository;
import youtube.repository.member.MemberRepository;
import youtube.repository.subscription.SubscriptionRepository;
import youtube.util.AcceptanceTest;

import static org.assertj.core.api.Assertions.*;
import static youtube.global.constant.CacheConstant.*;
import static youtube.util.TestConstant.*;


@AcceptanceTest
class CommandSubscribeTest {

    @Autowired
    private CommandSubscribe commandSubscribe;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private CacheManager cacheManager;

    @Test
    @DisplayName("회원이 채널을 구독하면 회원과 채널의 연결 엔티티인 Subscription이 생성됩니다")
    void subscribe() {
        // given
        Member member = Member.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .loginId(LoginId.from(TEST_LOGIN_ID.value))
                .password(Password.from(TEST_PASSWORD.value))
                .build();

        memberRepository.save(member);
        Channel channel = ChannelMapper.toEntity(member);
        channelRepository.save(channel);

        // when
        commandSubscribe.command(member.getId(), channel.getId());
        Subscription entity = subscriptionRepository.getById(1L);
        Cache cache = cacheManager.getCache(SUBSCRIBERS_COUNT);

        // then
        assertThat(subscriptionRepository.count()).isEqualTo(1);
        assertThat(entity.getMemberId()).isEqualTo(member.getId());
        assertThat(entity.getChannelId()).isEqualTo(channel.getId());
        assertThat(cache).isNotNull();
    }
}