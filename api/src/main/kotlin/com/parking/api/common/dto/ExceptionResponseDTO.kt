package com.parking.api.common.dto

import com.parking.domain.exception.enum.ExceptionCode

data class ExceptionResponseDTO<T> (
    val code: ExceptionCode? = null,
    val content: T?
)