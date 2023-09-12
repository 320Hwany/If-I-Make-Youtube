package youtube.application.video.video_info.media_type;

import org.springframework.http.MediaType;

public interface MediaTypeForExtension {

    boolean isSupport(final String fileExtension);

    MediaType getMediaType();
}
