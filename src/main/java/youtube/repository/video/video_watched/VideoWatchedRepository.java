package youtube.repository.video.video_watched;

import youtube.domain.video.video_watched.persist.VideoWatched;
import youtube.mapper.video.video_watched.dto.VideoWatchedResponse;

import java.util.List;
import java.util.Optional;

public interface VideoWatchedRepository {

    void save(final VideoWatched videoWatched);

    Optional<VideoWatched> findByMemberIdAndVideoInfoId(final long memberId, final long videoInfoId);

    List<VideoWatchedResponse> findVideoWatchedResponsesByMemberId(final long memberId);

    long count();
}
