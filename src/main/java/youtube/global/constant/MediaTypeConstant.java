package youtube.global.constant;

public enum MediaTypeConstant {

    PDF_EXTENSION(".pdf"),
    PNG_EXTENSION(".png"),
    MOV_EXTENSION(".mov"),
    AVI_EXTENSION(".avi"),
    MP4_EXTENSION(".mp4");

    public final String value;

    MediaTypeConstant(final String value) {
        this.value = value;
    }
}
