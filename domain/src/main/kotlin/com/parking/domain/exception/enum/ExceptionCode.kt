package com.parking.domain.exception.enum

enum class ExceptionCode(
    val message: String
) {
    MEMBER_NOT_FOUND("멤버 정보를 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH("패스워드가 일치하지 않습니다."),
    LOGIN_TRY_COUNT_LIMIT("로그인 시도 횟수가 초과하였습니다."),
    AUTHENTICATION_FAIL("인증에 실패하였습니다"),
    TOKEN_PARSE_FAIL("Token Parsing 에 실패하였습니다."),
    TOKEN_EXPIRED_ERROR("Token 이 만료 되었습니다.")
}