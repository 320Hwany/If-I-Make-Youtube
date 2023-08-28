package youtube.application.member;

import org.springframework.stereotype.Service;
import youtube.application.channel.command.CommandChannelSave;
import youtube.application.member.command.CommandMemberSave;
import youtube.domain.member.persist.Member;
import youtube.mapper.member.dto.MemberSignupRequest;


@Service
public class MemberSignupService {

    private final CommandMemberSave commandMemberSave;
    private final CommandChannelSave commandChannelSave;

    public MemberSignupService(final CommandMemberSave commandMemberSave,
                               final CommandChannelSave commandChannelSave) {
        this.commandMemberSave = commandMemberSave;
        this.commandChannelSave = commandChannelSave;
    }

    // 회원가입한 신규회원은 자동으로 채널 하나 생성
    public void signup(final MemberSignupRequest dto) {
        Member member = commandMemberSave.command(dto);
        commandChannelSave.command(member);
    }
}
