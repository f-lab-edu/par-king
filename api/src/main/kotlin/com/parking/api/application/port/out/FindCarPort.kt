package com.parking.api.application.port.out

import com.parking.domain.entity.Car

interface FindCarPort {
    fun findById(carId: Long): Car?

    fun findAllByMemberId(memberId: Long): List<Car>
}