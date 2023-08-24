package youtube.application.video.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import youtube.domain.channel.persist.Channel;
import youtube.domain.video.persist.Video;
import youtube.mapper.video.VideoMapper;
import youtube.mapper.video.dto.VideoSaveRequest;
import youtube.repository.channel.ChannelRepository;
import youtube.repository.video.VideoRepository;

@Service
public class CommandVideoUpload {

    private final ChannelRepository channelRepository;
    private final VideoRepository videoRepository;

    public CommandVideoUpload(final ChannelRepository channelRepository,
                              final VideoRepository videoRepository) {
        this.channelRepository = channelRepository;
        this.videoRepository = videoRepository;
    }

    @Transactional
    public void command(final MultipartFile uploadVideo, final long memberId, final VideoSaveRequest dto) {
        Channel channel = channelRepository.getByMemberId(memberId);
        Video video = VideoMapper.toEntity(channel.getId(), dto);
        videoRepository.save(video);
    }
}
