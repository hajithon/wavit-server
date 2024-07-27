package xyz.wavit.global.exception;

import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // COMMON
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다. 관리자에게 문의 바랍니다."),
    METHOD_ARGUMENT_NOT_VALID(BAD_REQUEST, "유효하지 않은 입력값입니다."),
    REGEX_NOT_VALID(BAD_REQUEST, "정규표현식 조건을 만족하지 않습니다."),

    // AUTH
    AUTH_ACCESS_DENIED(UNAUTHORIZED, "접근 권한이 없습니다."),
    INVALID_PASSWORD(UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    INVALID_JWT(UNAUTHORIZED, "유효하지 않은 JWT 토큰입니다."),
    AUTH_NOT_PARSABLE(INTERNAL_SERVER_ERROR, "시큐리티 인증 정보를 파싱할 수 없습니다."),
    AUTH_NOT_EXIST(INTERNAL_SERVER_ERROR, "시큐리티 인증 정보가 존재하지 않습니다."),
    NEW_PASSWORD_MISMATCH(CONFLICT, "새 비밀번호와 재확인용 비밀번호가 일치하지 않습니다."),
    AUTH_MANAGER_RESOURCE_NOT_ACCESIBLE(FORBIDDEN, "현재 관리자는 해당 리소스에 대한 접근 권한이 없습니다."),
    AUTH_INVALID_MANGER_VALIDATION(INTERNAL_SERVER_ERROR, "잘못된 방식으로 관리자 권한을 검증하였습니다. 서버 관리자에게 문의 바랍니다."),

    // USER
    USER_NOT_FOUND(NOT_FOUND, "사용자를 찾을 수 없습니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
