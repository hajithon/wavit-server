package xyz.wavit.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    private CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    private CustomException(ErrorCode errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public static CustomException from(ErrorCode errorCode) {
        return new CustomException(errorCode);
    }

    public static CustomException of(ErrorCode errorCode, String errorMessage) {
        return new CustomException(errorCode, errorMessage);
    }
}
