package domain.exception

import domain.exception.enum.ExceptionCode

class MemberException(
    override val exceptionCode: ExceptionCode,
    override val message: String?
) : BusinessException(exceptionCode, message)