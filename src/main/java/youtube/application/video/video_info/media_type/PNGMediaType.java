package youtube.application.video.video_info.media_type;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import static youtube.global.constant.MediaTypeConstant.PNG_EXTENSION;

@Service
public class PNGMediaType implements MediaTypeForExtension {

    @Override
    public boolean isSupport(final String fileExtension) {
        return fileExtension.equals(PNG_EXTENSION.value);
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.IMAGE_PNG;
    }
}
