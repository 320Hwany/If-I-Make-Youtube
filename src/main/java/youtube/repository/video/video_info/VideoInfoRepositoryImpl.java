package youtube.repository.video.video_info;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import youtube.domain.video.video_info.persist.VideoInfo;
import youtube.global.exception.NotFoundException;
import youtube.mapper.video.video_info.dto.QVideoInfoCacheDto;
import youtube.mapper.video.video_info.dto.VideoInfoCacheDto;

import static youtube.domain.video.video_info.persist.QVideoInfo.videoInfo;
import static youtube.global.constant.ExceptionMessageConstant.*;

@Repository
public class VideoInfoRepositoryImpl implements VideoInfoRepository {

    private final VideoInfoJpaRepository videoInfoJpaRepository;
    private final JPAQueryFactory queryFactory;

    public VideoInfoRepositoryImpl(final VideoInfoJpaRepository videoInfoJpaRepository,
                                   final JPAQueryFactory queryFactory) {
        this.videoInfoJpaRepository = videoInfoJpaRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public void save(final VideoInfo videoInfo) {
        videoInfoJpaRepository.save(videoInfo);
    }

    @Override
    public VideoInfo getById(final long videoInfoId) {
        return videoInfoJpaRepository.findById(videoInfoId)
                .orElseThrow(() -> new NotFoundException(VIDEO_INFO_NOT_FOUND.message));
    }

    @Override
    public VideoInfoCacheDto getVideoInfoCacheDtoById(final long videoInfoId) {
        VideoInfoCacheDto videoInfoCacheDto = queryFactory.select(new QVideoInfoCacheDto(
                        videoInfo.videoTitle,
                        videoInfo.videoDescription,
                        videoInfo.views,
                        videoInfo.likesCount,
                        videoInfo.dislikesCount,
                        videoInfo.createdAt
                ))
                .from(videoInfo)
                .where(videoInfo.id.eq(videoInfoId))
                .fetchFirst();

        if (videoInfoCacheDto != null) {
            return videoInfoCacheDto;
        }

        throw new NotFoundException(CHANNEL_NOT_FOUND.message);
    }

    @Override
    public long count() {
        return videoInfoJpaRepository.count();
    }
}
