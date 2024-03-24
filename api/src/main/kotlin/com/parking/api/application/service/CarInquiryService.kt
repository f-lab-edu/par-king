package com.parking.api.application.service

import com.parking.api.adapter.out.CarInquiryAdapter
import com.parking.api.adapter.out.MemberInquiryAdapter
import com.parking.api.adapter.out.ParkingLotInquiryAdapter
import com.parking.api.application.port.`in`.car.FindCarUseCase
import com.parking.api.application.vo.ResponseCarInfoVO
import com.parking.domain.entity.Car
import com.parking.domain.exception.CarException
import com.parking.domain.exception.MemberException
import com.parking.domain.exception.enum.ExceptionCode.*
import org.springframework.stereotype.Service

@Service
class CarInquiryService(
    private val carInquiryAdapter: CarInquiryAdapter,
    private val parkingLotInquiryAdapter: ParkingLotInquiryAdapter,
    private val memberInquiryAdapter: MemberInquiryAdapter
): FindCarUseCase {
    override fun findById(carId: Long): ResponseCarInfoVO {
        val car = carInquiryAdapter.findById(carId) ?: throw CarException(
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

        val id = memberInquiryAdapter.findIdByMemberId(memberId) ?: throw MemberException(
            MEMBER_NOT_FOUND,
            MEMBER_NOT_FOUND.message
        )

        val carList = id.let {carInquiryAdapter.findAllByMemberId(it) }

        return carList.map {
            getCarInfoVOFromCar(it)
        }
    }

    private fun getCarInfoVOFromCar(car: Car): ResponseCarInfoVO {
        val dibsOnParkingLotName =
            car.dibsOnParkingLotId?.let { parkingLotInquiryAdapter.findById(it)?.getParkingLotName() }

        return ResponseCarInfoVO.from(car, dibsOnParkingLotName)
    }
}