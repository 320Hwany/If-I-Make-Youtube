package youtube.exception.member;

import youtube.global.exception.NotFoundException;

import static youtube.global.constant.ExceptionMessageConstant.*;

public class MemberNotFoundException extends NotFoundException {

    private static final String MESSAGE = MEMBER_NOT_FOUND.message;

    public MemberNotFoundException() {
        super(MESSAGE);
    }
}
