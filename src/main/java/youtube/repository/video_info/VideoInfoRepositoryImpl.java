package youtube.repository.video_info;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import youtube.domain.video_info.persist.VideoInfo;
import youtube.domain.video_info.vo.VideoInfoCache;
import youtube.global.exception.NotFoundException;

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
    public VideoInfoCache getVideoInfoCacheDtoById(final long videoInfoId) {
        return null;
    }

    @Override
    public long count() {
        return videoInfoJpaRepository.count();
    }
}
