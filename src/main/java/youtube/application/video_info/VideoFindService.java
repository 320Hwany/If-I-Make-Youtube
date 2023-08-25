package youtube.application.video_info;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import youtube.global.exception.BadRequestException;
import youtube.global.exception.NotFoundException;

import java.net.MalformedURLException;
import java.nio.file.Path;

import static youtube.global.constant.ExceptionMessageConstant.*;

@Service
public class VideoFindService {

    @Value("${file.video}")
    private Path videoDir;

    public Resource loadAsResource(final String filename) {
        try {
            Path file = load(filename);
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

    private Path load(final String filename) {
        return videoDir.resolve(filename);
    }
}
