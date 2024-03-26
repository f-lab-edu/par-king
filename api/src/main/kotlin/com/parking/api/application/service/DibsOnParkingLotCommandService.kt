package com.parking.api.application.service

import com.parking.api.adapter.out.*
import com.parking.api.application.port.`in`.dibsOnParkingLot.DibsOnParkingLotUseCase
import com.parking.api.application.vo.DibsOnParkingLotVO
import com.parking.domain.entity.Car
import com.parking.domain.entity.DibsOnParkingLotStatus
import com.parking.domain.entity.ParkingLot
import com.parking.domain.exception.CarException
import com.parking.domain.exception.DibsOnParkingLotException
import com.parking.domain.exception.MemberException
import com.parking.domain.exception.ParkingLotException
import com.parking.domain.exception.enum.ExceptionCode.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DibsOnParkingLotCommandService(
    val memberInquiryAdapter: MemberInquiryAdapter,
    val carInquiryAdapter: CarInquiryAdapter,
    val carCommandAdapter: CarCommandAdapter,
    val parkingLotInquiryAdapter: ParkingLotInquiryAdapter,
    val parkingLotCommandAdapter: ParkingLotCommandAdapter,
    val dibsOnParkingLotCommandAdapter: DibsOnParkingLotCommandAdapter
): DibsOnParkingLotUseCase {

    //TODO 피드백 받기 이게 맞나? transactional 이 너무 넓게 잡힌다 안좋은 설계같다 사실상 save 에만 걸면 되는데
    //TODO 저장할 부분이 조회를 통해서 가져와야하고 그 과정에서 Exception 처리도 필요하다
    @Transactional
    override fun dibsOnParkingLot(dibsOnParkingLotVO: DibsOnParkingLotVO) {
        val memberId = findAvailableMemberId(dibsOnParkingLotVO)
        val parkingLot = findAvailableParkingLot(dibsOnParkingLotVO)
        val car = findAvailableCar(dibsOnParkingLotVO)

        val dibsOnParkingLot = dibsOnParkingLotCommandAdapter.save(dibsOnParkingLotVO.to(memberId))

        car.dibsOnParkingLot(parkingLot.parkingLotId!!, dibsOnParkingLot.dibsOnParkingLotId!!)

        parkingLotCommandAdapter.save(parkingLot)
        carCommandAdapter.save(car)
    }

    private fun findAvailableParkingLot(dibsOnParkingLotVO: DibsOnParkingLotVO): ParkingLot {
        val parkingLot = parkingLotInquiryAdapter.findById(dibsOnParkingLotVO.parkingLotId) ?: throw ParkingLotException(
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

    private fun findAvailableMemberId(dibsOnParkingLotVO: DibsOnParkingLotVO): Long {
        val memberId = memberInquiryAdapter.findIdByMemberId(dibsOnParkingLotVO.memberId) ?: throw MemberException(
            MEMBER_NOT_FOUND,
            MEMBER_NOT_FOUND.message
        )

        return memberId
    }

    private fun findAvailableCar(dibsOnParkingLotVO: DibsOnParkingLotVO): Car {
        val car = carInquiryAdapter.findById(dibsOnParkingLotVO.carId) ?: throw CarException(
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