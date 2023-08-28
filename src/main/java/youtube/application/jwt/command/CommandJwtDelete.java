package youtube.application.jwt.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.repository.jwt.JwtRepository;

@Service
public class CommandJwtDelete {

    private final JwtRepository jwtRepository;

    public CommandJwtDelete(final JwtRepository jwtRepository) {
        this.jwtRepository = jwtRepository;
    }

    @Transactional
    public void command(final long memberId) {
        jwtRepository.deleteByMemberId(memberId);
    }
}
