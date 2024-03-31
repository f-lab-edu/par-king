package com.parking.domain.exception

import com.parking.domain.exception.enum.ExceptionCode

data class NoShowException(
    override val exceptionCode: ExceptionCode,
    override val message: String?
) : BusinessException(exceptionCode, message)
