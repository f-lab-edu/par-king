package com.parking.api.adapter.out

import com.parking.api.application.port.out.FindCarPort
import com.parking.domain.entity.Car
import com.parking.jpa.repositories.CarRepository
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrNull

@Component
class CarInquiryAdapter(
    private val carRepository: CarRepository
): FindCarPort {
    override fun findById(carId: Long): Car? {
        return carRepository.findById(carId).getOrNull()?.to()
    }

    override fun findAllByMemberId(memberId: Long): List<Car> {
        return carRepository.findAllByMemberId(memberId).map { it.to() }
    }

}