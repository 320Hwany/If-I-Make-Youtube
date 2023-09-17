package youtube.repository.video.video_watched;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import youtube.domain.video.video_watched.persist.QVideoWatched;
import youtube.domain.video.video_watched.persist.VideoWatched;
import youtube.mapper.video.video_watched.dto.QVideoWatchedCacheDto;
import youtube.mapper.video.video_watched.dto.VideoWatchedCacheDto;

import java.util.List;

import static youtube.domain.video.video_watched.persist.QVideoWatched.videoWatched;

@Repository
public class VideoWatchedRepositoryImpl implements VideoWatchedRepository {

    private final VideoWatchedJpaRepository videoWatchedJpaRepository;
    private final JPAQueryFactory queryFactory;

    public VideoWatchedRepositoryImpl(final VideoWatchedJpaRepository videoWatchedJpaRepository,
                                      final JPAQueryFactory queryFactory) {
        this.videoWatchedJpaRepository = videoWatchedJpaRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public void save(final VideoWatched videoWatched) {
        videoWatchedJpaRepository.save(videoWatched);
    }

    @Override
    public List<VideoWatchedCacheDto> getVideoWatchedCacheDtosByMemberId(final long memberId) {
        return queryFactory.select(
                        new QVideoWatchedCacheDto(
                                videoWatched.videoInfoId,
                                videoWatched.lastWatchedDate
                        ))
                .from(videoWatched)
                .where(videoWatched.memberId.eq(memberId))
                .fetch();
    }

    @Override
    public long count() {
        return videoWatchedJpaRepository.count();
    }
}
