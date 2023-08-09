package youtube.repository.channel;

import org.springframework.data.jpa.repository.JpaRepository;
import youtube.domain.channel.persist.Channel;

import java.util.Optional;

public interface ChannelJpaRepository extends JpaRepository<Channel, Long> {

    Optional<Channel> findByMemberId(final long memberId);
}
