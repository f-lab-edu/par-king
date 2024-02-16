package domain.exception

import domain.exception.enum.ExceptionCode

sealed class BusinessException(
    open val exceptionCode: ExceptionCode,
    override val message: String?,
    override val cause: Throwable? = null
): RuntimeException(message, cause)