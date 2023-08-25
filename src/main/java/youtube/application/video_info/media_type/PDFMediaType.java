package youtube.application.video_info.media_type;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import static youtube.global.constant.MediaTypeConstant.PDF_EXTENSION;

@Service
public class PDFMediaType implements MediaTypeForExtension {

    @Override
    public boolean isSupport(final String fileExtension) {
        return fileExtension.equals(PDF_EXTENSION.value);
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.APPLICATION_PDF;
    }
}
