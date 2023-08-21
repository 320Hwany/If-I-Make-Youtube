package youtube.application.member.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.repository.jwt.JwtRepository;

@Service
public class CommandMemberLogout {

    private final JwtRepository jwtRepository;

    public CommandMemberLogout(final JwtRepository jwtRepository) {
        this.jwtRepository = jwtRepository;
    }

    @Transactional
    public void command(final long memberId) {
        jwtRepository.deleteByMemberId(memberId);
    }
}
