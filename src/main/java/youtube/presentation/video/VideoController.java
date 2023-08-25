package youtube.presentation.video;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import youtube.application.video_info.VideoUploadService;
import youtube.domain.member.vo.MemberSession;
import youtube.global.annotation.Login;
import youtube.mapper.video.dto.VideoSaveRequest;

@RequestMapping("/api")
@RestController
public class VideoController {

    private final VideoUploadService videoUploadService;

    public VideoController(final VideoUploadService videoUploadService) {
        this.videoUploadService = videoUploadService;
    }

    @PostMapping("/video")
    public void upload(@RequestPart final MultipartFile uploadVideo,
                       @RequestPart final VideoSaveRequest videoSaveRequest,
                       @Login final MemberSession memberSession) {
        videoUploadService.saveToServer(uploadVideo, memberSession.id(), videoSaveRequest);
    }
}
