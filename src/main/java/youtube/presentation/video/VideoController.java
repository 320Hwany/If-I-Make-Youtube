package youtube.presentation.video;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import youtube.application.video_info.VideoFindService;
import youtube.application.video_info.VideoUploadService;
import youtube.application.video_info.media_type.MediaTypeService;
import youtube.application.video_info.query.QueryVideoInfoCacheById;
import youtube.domain.member.vo.MemberSession;
import youtube.domain.video_info.vo.VideoInfoCache;
import youtube.global.annotation.Login;
import youtube.mapper.video_info.dto.VideoInfoSaveRequest;

@RequestMapping("/api")
@RestController
public class VideoController {

    private final QueryVideoInfoCacheById queryVideoInfoCacheById;
    private final VideoUploadService videoUploadService;
    private final VideoFindService videoFindService;
    private final MediaTypeService mediaTypeService;

    public VideoController(final QueryVideoInfoCacheById queryVideoInfoCacheById,
                           final VideoUploadService videoUploadService,
                           final VideoFindService videoFindService,
                           final MediaTypeService mediaTypeService) {
        this.queryVideoInfoCacheById = queryVideoInfoCacheById;
        this.videoUploadService = videoUploadService;
        this.videoFindService = videoFindService;
        this.mediaTypeService = mediaTypeService;
    }

    @GetMapping("/videos/{videoInfoId}")
    public ResponseEntity<Resource> getVideo(@PathVariable final long videoInfoId) {
        Resource resource = videoFindService.loadAsResource(videoInfoId);
        MediaType mediaType = mediaTypeService.getMediaType(videoInfoId);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(resource);
    }

    @GetMapping("/videoInfo/{videoInfoId}")
    public VideoInfoCache getVideoInfo(@PathVariable final long videoInfoId) {
        return queryVideoInfoCacheById.query(videoInfoId);
    }

    @PostMapping("/videos")
    public void upload(@RequestPart final MultipartFile uploadVideo,
                       @RequestPart final VideoInfoSaveRequest videoInfoSaveRequest,
                       @Login final MemberSession memberSession) {
        videoUploadService.saveToServer(uploadVideo, memberSession.id(), videoInfoSaveRequest);
    }
}
