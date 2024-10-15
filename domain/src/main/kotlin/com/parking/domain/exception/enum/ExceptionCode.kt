package com.parking.domain.exception.enum

enum class ExceptionCode(
    val message: String
) {
    //Member, 인증 관련
    MEMBER_NOT_FOUND("멤버 정보를 찾을 수 없습니다."),
    MEMBER_SAVE_ERROR("멤버를 저장하던 중 오류가 발생하였습니다."),
    MEMBER_REVOKED_FAIL("멤버 탈퇴에 실패하였습니다."),
    PASSWORD_NOT_MATCH("패스워드가 일치하지 않습니다."),
    LOGIN_TRY_COUNT_LIMIT("로그인 시도 횟수가 초과하였습니다."),
    AUTHENTICATION_FAIL("인증에 실패하였습니다"),
    TOKEN_PARSE_FAIL("Token Parsing 에 실패하였습니다."),
    TOKEN_EXPIRED_ERROR("Token 이 만료 되었습니다."),
    MEMBER_NOT_MATCH("접속한 유저와 요청한 유저가 일치하지 않습니다."),

    //ParkingLot 관련
    PARKING_LOT_ID_NULL_ERROR("주차장 ID가 없습니다."),
    PARKING_LOT_NOT_FOUND("주차장 정보를 찾을 수 없습니다."),
    PARKING_LOT_ALREADY_FULL("이미 주차장이 만석 입니다."),

    //Car 관련
    CAR_NOT_FOUND("자동차 정보를 찾을 수 없습니다."),

    //DibsOnParkingLot 관련
    DIBS_ON_PARKING_LOT_NOT_AVAILABLE("현재 찜 기능을 이용할 수 없는 상태입니다."),

    //NoShow 관련
    NO_SHOW_INVALID_REQUEST("잘못 요청된 No Show 요청입니다."),

    //SUCCESS
    SUCCESS("성공")
}