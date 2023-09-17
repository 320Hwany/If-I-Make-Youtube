package youtube.repository.video.video_watched;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import youtube.domain.video.video_watched.persist.VideoWatched;
import youtube.mapper.video.video_watched.dto.QVideoWatchedResponse;
import youtube.mapper.video.video_watched.dto.VideoWatchedResponse;

import java.util.List;
import java.util.Optional;

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
    public Optional<VideoWatched> findByMemberIdAndVideoInfoId(final long memberId, final long videoInfoId) {
        return videoWatchedJpaRepository.findByMemberIdAndVideoInfoId(memberId, videoInfoId);
    }

    @Override
    public List<VideoWatchedResponse> findVideoWatchedResponsesByMemberId(final long memberId) {
        return queryFactory.select(
                        new QVideoWatchedResponse(
                                videoWatched.videoInfoId,
                                videoWatched.lastWatchedDateTime
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
