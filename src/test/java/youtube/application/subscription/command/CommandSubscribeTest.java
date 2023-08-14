package youtube.application.subscription.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.application.channel.query.QueryChannelCache;
import youtube.domain.channel.persist.Channel;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.domain.member.vo.Password;
import youtube.domain.subscription.Subscription;
import youtube.global.exception.BadRequestException;
import youtube.mapper.channel.ChannelMapper;
import youtube.domain.channel.vo.ChannelCache;
import youtube.mapper.subscription.SubscriptionMapper;
import youtube.repository.channel.ChannelRepository;
import youtube.repository.member.MemberRepository;
import youtube.repository.subscription.SubscriptionRepository;
import youtube.util.AcceptanceTest;


import static org.assertj.core.api.Assertions.*;
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
    private QueryChannelCache queryChannelCache;

    @Test
    @DisplayName("이미 구독중인 채널은 여러번 구독할 수 없습니다")
    void subscribeDuplicate() {
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

        // expected
        assertThatThrownBy(() -> commandSubscribe.command(member.getId(), channel.getId()))
                .isInstanceOf(BadRequestException.class);
    }
    
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
        ChannelCache channelCache = queryChannelCache.getCache(channel.getId());

        // then
        assertThat(subscriptionRepository.count()).isEqualTo(1);
        assertThat(channelCache).isNotNull();
    }
}