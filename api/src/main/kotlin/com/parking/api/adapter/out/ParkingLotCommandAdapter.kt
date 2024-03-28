package com.parking.api.adapter.out

import com.parking.api.application.port.out.SaveParkingLotPort
import com.parking.domain.entity.ParkingLot
import com.parking.jpa.entity.ParkingLotEntity
import com.parking.jpa.repositories.ParkingLotRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ParkingLotCommandAdapter(
    private val parkingLotRepository: ParkingLotRepository
): SaveParkingLotPort {
    override fun save(parkingLot: ParkingLot): ParkingLot {
        return parkingLotRepository.save(ParkingLotEntity.from(parkingLot, null)).to()
    }

    override fun deleteUpdate(parkingLot: ParkingLot, deletedAt: LocalDateTime) {
        parkingLotRepository.save(ParkingLotEntity.from(parkingLot, deletedAt))
    }
}