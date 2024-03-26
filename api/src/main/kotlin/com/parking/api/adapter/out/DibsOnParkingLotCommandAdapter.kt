package com.parking.api.adapter.out

import com.parking.api.application.port.out.SaveDibsOnParkingLotPort
import com.parking.domain.entity.DibsOnParkingLot
import com.parking.jpa.entity.DibsOnParkingLotJpaEntity
import com.parking.jpa.repositories.DibsOnParkingLotJpaRepository
import org.springframework.stereotype.Component

@Component
class DibsOnParkingLotCommandAdapter(
    private val dibsOnParkingLotJpaRepository: DibsOnParkingLotJpaRepository
): SaveDibsOnParkingLotPort {
    override fun save(dibsOnParkingLot: DibsOnParkingLot): DibsOnParkingLot {
        return dibsOnParkingLotJpaRepository.save(DibsOnParkingLotJpaEntity.from(dibsOnParkingLot)).to()
    }
}