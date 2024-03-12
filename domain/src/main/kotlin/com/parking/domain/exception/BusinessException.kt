package com.parking.domain.exception

import com.parking.domain.exception.enum.ExceptionCode

sealed class BusinessException(
    open val exceptionCode: ExceptionCode,
    override val message: String?,
    override val cause: Throwable? = null
): RuntimeException(message, cause)