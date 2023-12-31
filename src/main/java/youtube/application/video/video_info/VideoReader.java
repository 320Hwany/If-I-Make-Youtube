package youtube.application.video.video_info;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import youtube.domain.video.video_info.persist.VideoInfo;
import youtube.global.exception.BadRequestException;
import youtube.global.exception.NotFoundException;

import java.net.MalformedURLException;
import java.nio.file.Path;

import static youtube.global.constant.ExceptionMessageConstant.*;

@Service
public class VideoReader {

    @Value("${file.video}")
    private Path videoDir;

    private final VideoInfoReader videoInfoReader;

    public VideoReader(final VideoInfoReader videoInfoReader) {
        this.videoInfoReader = videoInfoReader;
    }

    public Resource loadAsResource(final long videoInfoId) {
        String filename = getFilename(videoInfoId);

        try {
            Path file = videoDir.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new NotFoundException(VIDEO_NOT_FOUND.message);
            }

        } catch (MalformedURLException e) {
            throw new BadRequestException(MALFORMED_URL_BAD_REQUEST.message);
        }
    }

    private String getFilename(final long videoInfoId) {
        VideoInfo videoInfo = videoInfoReader.getByVideoInfoId(videoInfoId);
        return videoInfo.getId() + videoInfo.getFileExtension();
    }
}
