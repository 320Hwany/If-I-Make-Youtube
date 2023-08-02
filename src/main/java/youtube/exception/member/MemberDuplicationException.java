package youtube.exception.member;

import youtube.global.exception.BadRequestException;

import static youtube.global.constant.ExceptionMessageConstant.MEMBER_DUPLICATION;

public class MemberDuplicationException extends BadRequestException {

    private static final String MESSAGE = MEMBER_DUPLICATION.message;

    public MemberDuplicationException() {
        super(MESSAGE);
    }
}
