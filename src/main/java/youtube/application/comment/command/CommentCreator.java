package youtube.application.comment.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.application.video.video_info.query.VideoInfoCacheReader;
import youtube.domain.comment.Comment;
import youtube.domain.member.vo.Nickname;
import youtube.domain.video.video_info.vo.VideoInfoCache;
import youtube.mapper.comment.CommentMapper;
import youtube.mapper.comment.dto.CommentSaveRequest;
import youtube.repository.comment.CommentRepository;
import youtube.repository.member.MemberRepository;

@Service
public class CommentCreator {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final VideoInfoCacheReader videoInfoCacheReader;

    public CommentCreator(final CommentRepository commentRepository,
                          final MemberRepository memberRepository,
                          final VideoInfoCacheReader videoInfoCacheReader) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.videoInfoCacheReader = videoInfoCacheReader;
    }

    @Transactional
    public void command(final long memberId, final CommentSaveRequest dto) {
        Nickname nickname = memberRepository.getNicknameById(memberId);
        Comment entity = CommentMapper.toEntity(memberId, nickname, dto);
        commentRepository.save(entity);
        VideoInfoCache cache = videoInfoCacheReader.getByVideoInfoId(dto.videoInfoId());
        cache.plusOneCommentCount();
    }
}
