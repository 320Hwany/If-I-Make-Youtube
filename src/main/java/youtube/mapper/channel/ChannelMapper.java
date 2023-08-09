package youtube.mapper.channel;

import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.vo.Button;
import youtube.domain.channel.vo.ChannelName;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.Nickname;

public class ChannelMapper {

    private static final int ZERO = 0;

    private ChannelMapper() {
    }

    public static Channel toEntity(final Member member) {
        // 신규회원은 회원가입한 닉네임으로 채널명을 만듭니다
        Nickname nickname = member.getNickname();

        return Channel.builder()
                .memberId(member.getId())
                .channelName(ChannelName.from(nickname.getValue()))
                .videosCount(ZERO)
                .subscribersCount(ZERO)
                .button(Button.NORMAL)
                .build();
    }
}
