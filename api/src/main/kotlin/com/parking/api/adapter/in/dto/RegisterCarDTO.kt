package com.parking.api.adapter.`in`.dto

import com.parking.api.application.vo.RegisterCarVO

data class RegisterCarDTO (
    val memberId: String,
    val carNumber: String
) {
    fun toVO() = RegisterCarVO(
        memberId = memberId,
        carNumber = carNumber
    )
}