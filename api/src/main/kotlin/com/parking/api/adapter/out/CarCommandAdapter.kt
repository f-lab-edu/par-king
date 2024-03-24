package com.parking.api.adapter.out

import com.parking.api.application.port.out.SaveCarPort
import com.parking.domain.entity.Car
import com.parking.jpa.entity.CarJpaEntity
import com.parking.jpa.repositories.CarJpaRepository
import org.springframework.stereotype.Component

@Component
class CarCommandAdapter (
    private val carJpaRepository: CarJpaRepository
): SaveCarPort {
    override fun save(car: Car): Car {
        return carJpaRepository.save(CarJpaEntity.from(car)).to()
    }
}