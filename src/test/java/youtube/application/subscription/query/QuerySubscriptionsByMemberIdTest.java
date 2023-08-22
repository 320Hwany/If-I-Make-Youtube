package youtube.application.subscription.query;

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
import youtube.mapper.channel.ChannelMapper;
import youtube.mapper.subscription.SubscriptionMapper;
import youtube.mapper.subscription.dto.SubscriptionChannelsCache;
import youtube.repository.channel.ChannelRepository;
import youtube.repository.member.MemberRepository;
import youtube.repository.subscription.SubscriptionRepository;
import youtube.util.AcceptanceTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static youtube.global.constant.CacheConstant.SUBSCRIPTION_CHANNELS_CACHE;
import static youtube.util.TestConstant.*;

@AcceptanceTest
class QuerySubscriptionsByMemberIdTest {

    @Autowired
    private QuerySubscriptionsByMemberId querySubscriptionsByMemberId;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Test
    @DisplayName("회원이 구독한 채널 리스트 DB에서 가져온 후 캐시에 저장합니다")
    void querySubscriptionsByMemberId() {
        // given
        Member member = Member.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .loginId(LoginId.from(TEST_LOGIN_ID.value))
                .password(Password.from(TEST_PASSWORD.value))
                .build();
        memberRepository.save(member);

        Channel channel = ChannelMapper.toEntity(member);
        channelRepository.save(channel);

        Subscription subscription = SubscriptionMapper.toEntity(member.getId(), channel.getId());
        subscriptionRepository.save(subscription);

        // when
        List<SubscriptionChannelsCache> caches = querySubscriptionsByMemberId.query(member.getId());
        Cache cache = cacheManager.getCache(SUBSCRIPTION_CHANNELS_CACHE);

        // then
        assertThat(cache).isNotNull();
        assertThat(cache.get(member.getId())).isNotNull();
        assertThat(caches.size()).isEqualTo(1);
    }
}