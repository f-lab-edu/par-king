package com.parking.api.application.port.out

import com.parking.domain.entity.Car

interface SaveCarPort {
    fun save(car: Car): Car
}