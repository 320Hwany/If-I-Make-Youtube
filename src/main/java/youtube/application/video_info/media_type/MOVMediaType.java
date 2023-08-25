package youtube.application.video_info.media_type;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import static youtube.global.constant.MediaTypeConstant.*;

@Service
public class MOVMediaType implements MediaTypeForExtension {

    @Override
    public boolean isSupport(final String fileExtension) {
        return fileExtension.equals(MOV_EXTENSION.value);
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.parseMediaType("video/quicktime");
    }
}
