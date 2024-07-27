package xyz.wavit.global.exception;

public record ErrorResponse(String errorCodeName, String errorMessage) {

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.name(), errorCode.getMessage());
    }

    public static ErrorResponse of(ErrorCode errorCode, String errorMessage) {
        return new ErrorResponse(errorCode.name(), errorMessage);
    }
}