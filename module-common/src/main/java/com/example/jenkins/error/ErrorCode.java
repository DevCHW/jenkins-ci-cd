package com.example.jenkins.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // SERVER ERROR
    INTERNAL_SERVER_ERROR(500, "SERVER-001", "서버가 요청을 처리할 수 없습니다."),
    JSON_PARSING_ERROR(500, "SERVER-002", "JSON 파싱에 실패하였습니다."),
    INVALID_INPUT_VALUE(400, "COMMON-001", "유효성 검증에 실패하였습니디."),

    // MEMBER
    CHANGE_NICKNAME_FAIL(422, "MEMBER-001", "닉네임 변경에 실패하였습니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
