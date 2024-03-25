package com.parking.api.application.service

import com.parking.api.adapter.out.MemberInquiryAdapter
import com.parking.api.adapter.out.ParkingLotCommandAdapter
import com.parking.api.adapter.out.ParkingLotInquiryAdapter
import com.parking.api.application.port.`in`.parkingLot.CreateParkingLotUseCase
import com.parking.api.application.port.`in`.parkingLot.DeleteParkingLotUseCase
import com.parking.api.application.port.`in`.parkingLot.ModifyParkingLotUseCase
import com.parking.api.application.vo.ParkingLotInfoVO
import com.parking.domain.entity.ParkingLotInfo
import com.parking.domain.entity.ParkingLotLocation
import com.parking.domain.exception.MemberException
import com.parking.domain.exception.ParkingLotException
import com.parking.domain.exception.enum.ExceptionCode.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ParkingLotCommandService(
    private val memberInquiryAdapter: MemberInquiryAdapter,
    private val parkingLotInquiryAdapter: ParkingLotInquiryAdapter,
    private val parkingLotCommandAdapter: ParkingLotCommandAdapter
): CreateParkingLotUseCase, DeleteParkingLotUseCase, ModifyParkingLotUseCase {

    @Transactional
    override fun create(parkingLotInfoVO: ParkingLotInfoVO): ParkingLotInfoVO {
        val memberId = memberInquiryAdapter.findIdByMemberId(parkingLotInfoVO.memberId) ?: throw MemberException(
            MEMBER_NOT_FOUND,
            MEMBER_NOT_FOUND.message
        )

        return ParkingLotInfoVO.from(
            parkingLotInfoVO.memberId,
            parkingLotCommandAdapter.save(parkingLotInfoVO.toParkingLot(memberId))
        )
    }

    @Transactional
    override fun delete(parkingLotId: Long) {
        val parkingLot = parkingLotInquiryAdapter.findById(parkingLotId) ?: throw ParkingLotException(
            PARKING_LOT_NOT_FOUND,
            PARKING_LOT_NOT_FOUND.message
        )

        parkingLotCommandAdapter.deleteUpdate(parkingLot, LocalDateTime.now())
    }

    @Transactional
    override fun modify(parkingLotInfoVO: ParkingLotInfoVO): ParkingLotInfoVO {
        if (parkingLotInfoVO.parkingLotId == null) throw ParkingLotException(
            PARKING_LOT_ID_NULL_ERROR,
            PARKING_LOT_ID_NULL_ERROR.message
        )

        val parkingLot = parkingLotInquiryAdapter.findById(parkingLotInfoVO.parkingLotId) ?: throw ParkingLotException(
            PARKING_LOT_NOT_FOUND,
            PARKING_LOT_NOT_FOUND.message
        )

        val parkingLotInfo = ParkingLotInfo(
            parkingLotInfoVO.name,
            parkingLotInfoVO.fullAddress,
            parkingLotInfoVO.totalSpace,
            parkingLotInfoVO.occupiedSpace,
            parkingLotInfoVO.cost,
            parkingLotInfoVO.extraCost
        )

        val parkingLotLocation = ParkingLotLocation(parkingLotInfoVO.cityName, parkingLotInfoVO.guName)

        parkingLot.modifyParkingLot(parkingLotInfo, parkingLotLocation)

        return ParkingLotInfoVO.from(parkingLotInfoVO.memberId, parkingLotCommandAdapter.save(parkingLot))
    }
}