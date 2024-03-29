package com.parking.api.adapter.out

import com.parking.api.application.port.out.SaveDibsOnParkingLotPort
import com.parking.domain.entity.DibsOnParkingLot
import com.parking.jpa.entity.DibsOnParkingLotEntity
import com.parking.jpa.repositories.DibsOnParkingLotRepository
import org.springframework.stereotype.Component

@Component
class DibsOnParkingLotCommandAdapter(
    private val dibsOnParkingLotRepository: DibsOnParkingLotRepository
): SaveDibsOnParkingLotPort {
    override fun save(dibsOnParkingLot: DibsOnParkingLot): DibsOnParkingLot {
        return dibsOnParkingLotRepository.save(DibsOnParkingLotEntity.from(dibsOnParkingLot)).to()
    }
}