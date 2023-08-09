package youtube.repository.channel;

import org.springframework.data.jpa.repository.JpaRepository;
import youtube.domain.channel.persist.Channel;

public interface ChannelJpaRepository extends JpaRepository<Channel, Long> {
}
