package com.parking.api.application.port.`in`.car

import com.parking.api.application.vo.ResponseCarInfoVO

interface FindCarUseCase {
    fun findById(carId: Long): ResponseCarInfoVO

    fun findAllByMemberId(currentUserName: String, memberId: String): List<ResponseCarInfoVO>
}