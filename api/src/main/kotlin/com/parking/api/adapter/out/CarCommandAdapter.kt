package com.parking.api.adapter.out

import com.parking.api.application.port.out.SaveCarPort
import com.parking.domain.entity.Car
import com.parking.jpa.entity.CarEntity
import com.parking.jpa.repositories.CarRepository
import org.springframework.stereotype.Component

@Component
class CarCommandAdapter (
    private val carRepository: CarRepository
): SaveCarPort {
    override fun save(car: Car): Car {
        return carRepository.save(CarEntity.from(car)).to()
    }
}