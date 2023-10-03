package youtube.mapper.channel;

import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.vo.Button;
import youtube.domain.channel.vo.ChannelCache;
import youtube.domain.channel.vo.ChannelDescription;
import youtube.domain.channel.vo.ChannelName;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.Nickname;
import youtube.mapper.channel.dto.ChannelCacheDto;

import java.util.concurrent.atomic.AtomicInteger;

import static youtube.global.constant.NumberConstant.ZERO;

public enum ChannelMapper {

    ChannelMapper() {
    };

    private static final String BLANK = "";

    // 신규회원은 회원가입한 닉네임으로 채널명을 만듭니다
    public static Channel toEntity(final Member member) {
        Nickname nickname = member.getNickname();

        return Channel.builder()
                .memberId(member.getId())
                .channelName(ChannelName.from(nickname.getNickname()))
                .channelDescription(ChannelDescription.from(BLANK))
                .videosCount(ZERO.value)
                .subscribersCount(ZERO.value)
                .button(Button.NORMAL)
                .isInfluencer(false)
                .build();
    }

    public static ChannelCache toChannelCache(final ChannelCacheDto channelCacheDto) {
        AtomicInteger atomicSubscribersCount = toAtomicType(channelCacheDto.subscribersCount());

        return ChannelCache.builder()
                .channelName(channelCacheDto.channelName())
                .channelDescription(channelCacheDto.channelDescription())
                .videosCount(channelCacheDto.videosCount())
                .subscribersCount(atomicSubscribersCount)
                .build();
    }

    private static AtomicInteger toAtomicType(final int subscribersCount) {
        return new AtomicInteger(subscribersCount);
    }
}
