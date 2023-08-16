package youtube.application.subscription.command;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import youtube.application.channel.query.QueryChannelCacheById;
import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.vo.ChannelCache;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.domain.member.vo.Password;
import youtube.domain.subscription.Subscription;
import youtube.global.exception.NotFoundException;
import youtube.mapper.channel.ChannelMapper;
import youtube.mapper.subscription.SubscriptionMapper;
import youtube.repository.channel.ChannelRepository;
import youtube.repository.member.MemberRepository;
import youtube.repository.subscription.SubscriptionRepository;
import youtube.util.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static youtube.util.TestConstant.*;

@AcceptanceTest
class CommandSubscriptionCancelTest {

    @Autowired
    private CommandSubscriptionCancel commandSubscriptionCancel;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private QueryChannelCacheById queryChannelCacheById;

    @Test
    @DisplayName("구독 취소 요청을 한 채널의 정보가 구독 테이블에 없으면 예외가 발생합니다")
    void subscriptionCancelFail() {
        // expected
        assertThatThrownBy(() -> commandSubscriptionCancel.command(9999, 9999))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("구독을 취소하면 회원, 채널의 연결 테이블인 구독 테이블의 정보가 삭제되고 구독자 수가 1 감소합니다")
    void subscriptionCancelSuccess() {
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
        commandSubscriptionCancel.command(member.getId(), channel.getId());
        ChannelCache channelCache = queryChannelCacheById.query(channel.getId());

        // then
        assertThat(subscriptionRepository.count()).isEqualTo(0);
        assertThat(channelCache.getSubscribersCount()).isEqualTo(-1);
    }
}