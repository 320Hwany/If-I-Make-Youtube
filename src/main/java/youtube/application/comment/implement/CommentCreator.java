package youtube.application.comment.implement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.application.video.video_info.implement.VideoInfoCacheReader;
import youtube.domain.comment.Comment;
import youtube.domain.member.vo.MemberSession;
import youtube.domain.video.video_info.vo.VideoInfoCache;
import youtube.mapper.comment.CommentMapper;
import youtube.mapper.comment.dto.CommentSaveRequest;
import youtube.repository.comment.CommentRepository;

@Service
public class CommentCreator {

    private final CommentRepository commentRepository;
    private final VideoInfoCacheReader videoInfoCacheReader;

    public CommentCreator(final CommentRepository commentRepository,
                          final VideoInfoCacheReader videoInfoCacheReader) {
        this.commentRepository = commentRepository;
        this.videoInfoCacheReader = videoInfoCacheReader;
    }

    @Transactional
    public void command(final MemberSession memberSession, final CommentSaveRequest dto) {
        Comment entity = CommentMapper.toEntity(memberSession, dto);
        commentRepository.save(entity);
        VideoInfoCache cache = videoInfoCacheReader.getByVideoInfoId(dto.videoInfoId());
        cache.plusOneCommentCount();
    }
}
