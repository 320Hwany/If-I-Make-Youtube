package youtube.application.video_info.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.domain.channel.persist.Channel;
import youtube.domain.video_info.persist.VideoInfo;
import youtube.mapper.video_info.VideoInfoMapper;
import youtube.mapper.video_info.dto.VideoInfoSaveRequest;
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
    public long command(final long memberId, final VideoInfoSaveRequest dto, final String fileExtension) {
        Channel channel = channelRepository.getByMemberId(memberId);
        VideoInfo videoInfo = VideoInfoMapper.toEntity(channel.getId(), dto, fileExtension);
        videoInfoRepository.save(videoInfo);
        return videoInfo.getId();
    }
}
