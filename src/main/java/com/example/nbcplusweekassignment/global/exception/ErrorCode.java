package com.example.nbcplusweekassignment.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //User
    INVALID_USER_ID_AND_PASSWORD_EXCEPTION(400, "닉네임 또는 패스워드를 확인해주세요"),
    NOT_SAME_PASSWORD_EXCEPTION(400, "비밀번호가 일치하지 않습니다"),
    ALREADY_EXIST_NICKNAME_EXCEPTION(409, "중복된 닉네임입니다."),
    //불가는 에러코드 뭐로하지?
    PASSWORD_CONTAINS_NICKNAME_EXCEPTION(400, "비밀번호에 닉네임이 포함되어서는 안됩니다."),
    NOT_FOUND_USER_EXCEPTION(400, "해당 유저가 존재하지 않습니다"),

    //JWT
    EMPTY_CLAIMS_JWT_EXCEPTION(400, "잘못된 형식의 JWT 토큰입니다."),
    EXPIRED_JWT_EXCEPTION(400, "기한이 만료된 JWT 토큰입니다."),
    INVALID_JWT_SIGNATURE_EXCEPTION(400, "유효하지 않은 서명의 JWT 토큰입니다"),
    UNSUPPORTED_JWT_EXCEPTION(400, "지원하지 않는 형식의 JWT 토큰입니다."),

    //Post
    NOT_FOUND_POST_EXCEPTION(400, "해당 게시글이 존재하지 않습니다."),

    //Common
    NOT_AUTHOR_EXCEPTION(400, "해당 내용의 작성자가 아닙니다.");

    private final int status;

    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this. message = message;
    }
}
