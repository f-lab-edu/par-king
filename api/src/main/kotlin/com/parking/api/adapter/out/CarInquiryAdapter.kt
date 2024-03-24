package com.parking.api.adapter.out

import com.parking.api.application.port.out.FindCarPort
import com.parking.domain.entity.Car
import com.parking.jpa.repositories.CarJpaRepository
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrNull

@Component
class CarInquiryAdapter(
    private val carJpaRepository: CarJpaRepository
): FindCarPort {
    override fun findById(carId: Long): Car? {
        return carJpaRepository.findById(carId).getOrNull()?.to()
    }

    override fun findAllByMemberId(memberId: Long): List<Car> {
        return carJpaRepository.findAllByMemberId(memberId).map { it.to() }
    }

}