package youtube.presentation.video;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import youtube.application.video_info.VideoFindService;
import youtube.application.video_info.VideoUploadService;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;
import youtube.mapper.video_info.dto.VideoInfoSaveRequest;

@RequestMapping("/api")
@RestController
public class VideoController {

    private final VideoUploadService videoUploadService;
    private final VideoFindService videoFindService;

    public VideoController(final VideoUploadService videoUploadService,
                           final VideoFindService videoFindService) {
        this.videoUploadService = videoUploadService;
        this.videoFindService = videoFindService;
    }

    @GetMapping("/video/{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable final String filename) {
        Resource resource = videoFindService.loadAsResource(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }

    @PostMapping("/video")
    public void upload(@RequestPart final MultipartFile uploadVideo,
                       @RequestPart final VideoInfoSaveRequest videoInfoSaveRequest,
                       @Login final MemberSession memberSession) {
        videoUploadService.saveToServer(uploadVideo, memberSession.id(), videoInfoSaveRequest);
    }
}
