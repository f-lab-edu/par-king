package com.parking.domain.exception

import com.parking.domain.exception.enum.ExceptionCode

class CarException(
    override val exceptionCode: ExceptionCode,
    override val message: String?
) : BusinessException(exceptionCode, message)