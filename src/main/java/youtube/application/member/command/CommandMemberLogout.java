package youtube.application.member.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.repository.jwt.JwtRepository;

@Transactional
@Service
public class CommandMemberLogout {

    private final JwtRepository jwtRepository;

    public CommandMemberLogout(final JwtRepository jwtRepository) {
        this.jwtRepository = jwtRepository;
    }

    public void command(final long memberId) {
        jwtRepository.deleteByMemberId(memberId);
    }
}
