package domain.exception.enum

enum class ExceptionCode(
    val message: String
) {
    MEMBER_NOT_FOUND("멤버 정보를 찾을 수 없습니다.")
}