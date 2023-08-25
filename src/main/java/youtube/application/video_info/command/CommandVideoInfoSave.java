package youtube.application.video_info.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.channel.persist.Channel;
import youtube.domain.video_info.persist.VideoInfo;
import youtube.mapper.video.VideoMapper;
import youtube.mapper.video.dto.VideoSaveRequest;
import youtube.repository.channel.ChannelRepository;
import youtube.repository.video_info.VideoInfoRepository;

@Service
public class CommandVideoInfoSave {

    private final ChannelRepository channelRepository;
    private final VideoInfoRepository videoInfoRepository;

    public CommandVideoInfoSave(final ChannelRepository channelRepository,
                                final VideoInfoRepository videoInfoRepository) {
        this.channelRepository = channelRepository;
        this.videoInfoRepository = videoInfoRepository;
    }

    @Transactional
    public long command(final long memberId, final VideoSaveRequest dto) {
        Channel channel = channelRepository.getByMemberId(memberId);
        VideoInfo videoInfo = VideoMapper.toEntity(channel.getId(), dto);
        videoInfoRepository.save(videoInfo);
        return videoInfo.getId();
    }
}
