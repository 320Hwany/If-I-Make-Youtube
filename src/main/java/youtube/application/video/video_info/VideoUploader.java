package youtube.application.video.video_info;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import youtube.global.exception.BadRequestException;
import youtube.mapper.video.video_info.dto.VideoInfoSaveRequest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static youtube.global.constant.ExceptionMessageConstant.*;
import static youtube.global.constant.StringConstant.*;

@Service
public class VideoUploader {

    @Value("${file.video}")
    private Path videoDir;

    private final VideoInfoCreator videoInfoCreator;

    public VideoUploader(final VideoInfoCreator videoInfoCreator) {
        this.videoInfoCreator = videoInfoCreator;
    }

    public void saveToServer(final MultipartFile uploadVideo, final long memberId, final VideoInfoSaveRequest dto) {
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

    private String getFileName(final MultipartFile uploadVideo, final long memberId, final VideoInfoSaveRequest dto) {
        String originalFilename = uploadVideo.getOriginalFilename();
        assert originalFilename != null;
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(DOT.value));

        long videoInfoId = videoInfoCreator.command(memberId, dto, fileExtension);
        return videoInfoId + fileExtension;
    }
}
