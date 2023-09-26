package youtube.presentation.video.video_info;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import youtube.application.video.video_info.VideoReader;
import youtube.application.video.video_info.VideoUploader;
import youtube.application.video.video_info.media_type.MediaTypeReader;
import youtube.application.video.video_info.query.VideoInfoCacheReader;
import youtube.domain.member.vo.MemberSession;
import youtube.domain.video.video_info.vo.VideoInfoCache;
import youtube.global.annotation.Login;
import youtube.mapper.video.video_info.dto.VideoInfoSaveRequest;

@RequestMapping("/api")
@RestController
public class VideoInfoController {

    private final VideoInfoCacheReader videoInfoCacheReader;
    private final VideoUploader videoUploader;
    private final VideoReader videoReader;
    private final MediaTypeReader mediaTypeReader;

    public VideoInfoController(final VideoInfoCacheReader videoInfoCacheReader,
                               final VideoUploader videoUploader,
                               final VideoReader videoReader,
                               final MediaTypeReader mediaTypeReader) {
        this.videoInfoCacheReader = videoInfoCacheReader;
        this.videoUploader = videoUploader;
        this.videoReader = videoReader;
        this.mediaTypeReader = mediaTypeReader;
    }

    @GetMapping("/videos/{videoInfoId}")
    public ResponseEntity<Resource> getVideo(@PathVariable final long videoInfoId) {
        Resource resource = videoReader.loadAsResource(videoInfoId);
        MediaType mediaType = mediaTypeReader.getMediaType(videoInfoId);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(resource);
    }

    @GetMapping("/video-info-cache/{videoInfoId}")
    public VideoInfoCache getVideoInfoCache(@PathVariable final long videoInfoId) {
        return videoInfoCacheReader.query(videoInfoId);
    }

    @PostMapping("/videos")
    public void upload(@RequestPart final MultipartFile uploadVideo,
                       @RequestPart final VideoInfoSaveRequest videoInfoSaveRequest,
                       @Login final MemberSession memberSession) {
        videoUploader.saveToServer(uploadVideo, memberSession.id(), videoInfoSaveRequest);
    }
}
