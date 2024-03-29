package com.parking.api.application.port.`in`.car

import com.parking.api.application.vo.RegisterCarVO
import com.parking.api.application.vo.ResponseCarInfoVO

interface SaveCarUseCase {
    fun save(carVO: RegisterCarVO): ResponseCarInfoVO
}