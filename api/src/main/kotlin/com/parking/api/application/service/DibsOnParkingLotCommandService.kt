package com.parking.api.application.service

import com.parking.api.application.port.`in`.dibsOnParkingLot.DibsOnParkingLotUseCase
import com.parking.api.application.port.out.*
import com.parking.api.application.vo.DibsOnParkingLotVO
import com.parking.domain.entity.Car
import com.parking.domain.entity.DibsOnParkingLotStatus
import com.parking.domain.entity.ParkingLot
import com.parking.domain.exception.CarException
import com.parking.domain.exception.DibsOnParkingLotException
import com.parking.domain.exception.ParkingLotException
import com.parking.domain.exception.enum.ExceptionCode.*
import com.parking.redis.lock.RedisLock
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DibsOnParkingLotCommandService(
    val findCarPort: FindCarPort,
    val saveCarPort: SaveCarPort,
    val findParkingLotPort: FindParkingLotPort,
    val saveParkingLotPort: SaveParkingLotPort,
    val saveDibsOnParkingLotPort: SaveDibsOnParkingLotPort
): DibsOnParkingLotUseCase {

    @Transactional
    @RedisLock("parkingLotId")
    override fun dibsOnParkingLot(memberId: Long, parkingLotId: Long, dibsOnParkingLotVO: DibsOnParkingLotVO) {
        val parkingLot = findAvailableParkingLot(dibsOnParkingLotVO)
        val car = findAvailableCar(dibsOnParkingLotVO)

        val dibsOnParkingLot = saveDibsOnParkingLotPort.save(dibsOnParkingLotVO.to(memberId))

        car.dibsOnParkingLot(parkingLotId, dibsOnParkingLot.dibsOnParkingLotId!!)

        saveParkingLotPort.save(parkingLot)
        saveCarPort.save(car)
    }

    private fun findAvailableParkingLot(dibsOnParkingLotVO: DibsOnParkingLotVO): ParkingLot {
        val parkingLot = findParkingLotPort.findById(dibsOnParkingLotVO.parkingLotId) ?: throw ParkingLotException(
            PARKING_LOT_NOT_FOUND,
            PARKING_LOT_NOT_FOUND.message
        )

        if (!parkingLot.occupyParkingLot()) {
            throw ParkingLotException(
                PARKING_LOT_ALREADY_FULL,
                PARKING_LOT_ALREADY_FULL.message
            )
        }

        return parkingLot
    }

    private fun findAvailableCar(dibsOnParkingLotVO: DibsOnParkingLotVO): Car {
        val car = findCarPort.findById(dibsOnParkingLotVO.carId) ?: throw CarException(
            CAR_NOT_FOUND,
            CAR_NOT_FOUND.message
        )

        if (!isAvailableParkingLot(car)) {
            throw DibsOnParkingLotException(
                DIBS_ON_PARKING_LOT_NOT_AVAILABLE,
                DIBS_ON_PARKING_LOT_NOT_AVAILABLE.message
            )
        }

        return car
    }

    private fun isAvailableParkingLot(car: Car): Boolean {
        if (car.dibsOnParkingLotId == null) return true

        return when (car.dibsOnParkingLotStatus) {
            DibsOnParkingLotStatus.NORMAL -> true
            else -> false
        }
    }
}