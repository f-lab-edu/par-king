package com.parking.api.application.port.`in`.car

import com.parking.api.application.vo.CarInfoVO

interface FindCarUseCase {
    fun findById(carId: Long): CarInfoVO

    fun findAllByMemberId(memberId: String): List<CarInfoVO>
}