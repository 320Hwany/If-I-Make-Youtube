package youtube.application.video_info;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import youtube.application.video_info.command.CommandVideoInfoSave;
import youtube.global.exception.BadRequestException;
import youtube.mapper.video.dto.VideoSaveRequest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static youtube.global.constant.ExceptionMessageConstant.*;
import static youtube.global.constant.StringConstant.*;

@Service
public class VideoUploadService {

    @Value("${file.video}")
    private Path videoDir;

    private final CommandVideoInfoSave commandVideoInfoSave;

    public VideoUploadService(final CommandVideoInfoSave commandVideoInfoSave) {
        this.commandVideoInfoSave = commandVideoInfoSave;
    }

    public void saveToServer(final MultipartFile uploadVideo, final long memberId, final VideoSaveRequest dto) {
        String fileName = getFileName(uploadVideo, memberId, dto);

        try {
            if (uploadVideo.isEmpty()) {
                throw new BadRequestException(EMPTY_VIDEO_BAD_REQUEST.message);
            }

            Path destinationFile = videoDir.resolve(Paths.get(fileName));

            // security check
            if (!destinationFile.getParent().equals(videoDir.toAbsolutePath())) {
                throw new BadRequestException(VIDEO_UPLOAD_SECURITY_BAD_REQUEST.message);
            }

            try (InputStream inputStream = uploadVideo.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new BadRequestException(SAVE_TO_VIDEO_BAD_REQUEST.message);
        }
    }

    private String getFileName(final MultipartFile uploadVideo, final long memberId, final VideoSaveRequest dto) {
        long videoId = commandVideoInfoSave.command(memberId, dto);

        String originalFilename = uploadVideo.getOriginalFilename();
        assert originalFilename != null;
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(DOT.value));
        return videoId + fileExtension;
    }
}
