package youtube.application.channel.command;

import lombok.extern.slf4j.Slf4j;
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
import youtube.mapper.channel.ChannelMapper;
import youtube.repository.channel.ChannelRepository;
import youtube.repository.member.MemberRepository;
import youtube.util.AcceptanceTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static youtube.util.TestConstant.*;

@Slf4j
@AcceptanceTest
class CommandChannelSubscriptionCountUpdateTest {

    @Autowired
    private CommandChannelSubscriptionCountUpdate commandChannelSubscriptionCountUpdate;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private QueryChannelCacheById queryChannelCacheById;

    @Test
    @DisplayName("캐시에 있는 구독자 수를 DB와 동기화합니다")
    void commandChannelSubscribersUpdate() throws ExecutionException, InterruptedException {
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
        CompletableFuture<Void> future = asyncWork(channel);
        future.get();

        // then
        Channel psChannel = channelRepository.getById(channel.getId());
        assertThat(psChannel.getSubscribersCount()).isEqualTo(10);
    }

    private CompletableFuture<Void> asyncWork(final Channel channel) {
        // given
        ChannelCache channelCache = queryChannelCacheById.query(channel.getId());

        // when
        for (int i = 0; i < 10; i++) {
            channelCache.increaseSubscribersCount();
        }
        return commandChannelSubscriptionCountUpdate.command(channel.getId(), channelCache);
    }
}