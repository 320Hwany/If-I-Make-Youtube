package youtube.domain.member.exception;

import youtube.global.exception.BadRequestException;

import static youtube.global.constant.ExceptionMessageConstant.*;

public class NickNameLengthException extends BadRequestException {

    private static final String MESSAGE = NICKNAME_LENGTH.message;

    public NickNameLengthException() {
        super(MESSAGE);
    }
}
