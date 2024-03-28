package com.parking.api.application.service

import com.parking.api.application.port.`in`.car.FindCarUseCase
import com.parking.api.application.port.out.FindCarPort
import com.parking.api.application.port.out.FindMemberPort
import com.parking.api.application.port.out.FindParkingLotPort
import com.parking.api.application.vo.ResponseCarInfoVO
import com.parking.domain.entity.Car
import com.parking.domain.exception.CarException
import com.parking.domain.exception.MemberException
import com.parking.domain.exception.enum.ExceptionCode.*
import org.springframework.stereotype.Service

@Service
class CarInquiryService(
    private val findCarPort: FindCarPort,
    private val findParkingLotPort: FindParkingLotPort,
    private val findMemberPort: FindMemberPort
): FindCarUseCase {
    override fun findById(carId: Long): ResponseCarInfoVO {
        val car = findCarPort.findById(carId) ?: throw CarException(
            CAR_NOT_FOUND,
            CAR_NOT_FOUND.message
        )

        return getCarInfoVOFromCar(car)
    }

    override fun findAllByMemberId(currentUserName: String, memberId: String): List<ResponseCarInfoVO> {
        if (currentUserName.compareTo(memberId) != 0) {
            throw MemberException(
                MEMBER_NOT_MATCH,
                MEMBER_NOT_MATCH.message
            )
        }

        val id = findMemberPort.findIdByMemberId(memberId) ?: throw MemberException(
            MEMBER_NOT_FOUND,
            MEMBER_NOT_FOUND.message
        )

        val carList = id.let {findCarPort.findAllByMemberId(it) }

        return carList.map {
            getCarInfoVOFromCar(it)
        }
    }

    private fun getCarInfoVOFromCar(car: Car): ResponseCarInfoVO {
        val dibsOnParkingLotName =
            car.dibsOnParkingLotId?.let { findParkingLotPort.findById(it)?.getParkingLotName() }

        return ResponseCarInfoVO.from(car, dibsOnParkingLotName)
    }
}