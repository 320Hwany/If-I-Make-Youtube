package youtube.mapper.channel;

import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.vo.Button;
import youtube.domain.channel.vo.ChannelDescription;
import youtube.domain.channel.vo.ChannelName;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.Nickname;

public class ChannelMapper {

    private static final int ZERO = 0;
    private static final String BLANK = "";

    private ChannelMapper() {
    }

    // 신규회원은 회원가입한 닉네임으로 채널명을 만듭니다
    public static Channel toEntity(final Member member) {
        Nickname nickname = member.getNickname();

        return Channel.builder()
                .memberId(member.getId())
                .channelName(ChannelName.from(nickname.getValue()))
                .channelDescription(ChannelDescription.from(BLANK))
                .videosCount(ZERO)
                .subscribersCount(ZERO)
                .button(Button.NORMAL)
                .build();
    }
}
