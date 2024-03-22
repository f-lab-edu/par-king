package com.parking.api.application.service

import com.parking.api.adapter.out.MemberInquiryAdapter
import com.parking.api.adapter.out.ParkingLotCommandAdapter
import com.parking.api.adapter.out.ParkingLotInquiryAdapter
import com.parking.api.application.port.`in`.parkingLot.CreateParkingLotUseCase
import com.parking.api.application.port.`in`.parkingLot.DeleteParkingLotUseCase
import com.parking.api.application.vo.ParkingLotInfoVO
import com.parking.domain.exception.MemberException
import com.parking.domain.exception.ParkingLotException
import com.parking.domain.exception.enum.ExceptionCode.MEMBER_NOT_FOUND
import com.parking.domain.exception.enum.ExceptionCode.PARKING_LOT_NOT_FOUND
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ParkingLotCommandService(
    private val memberInquiryAdapter: MemberInquiryAdapter,
    private val parkingLotInquiryAdapter: ParkingLotInquiryAdapter,
    private val parkingLotCommandAdapter: ParkingLotCommandAdapter
): CreateParkingLotUseCase, DeleteParkingLotUseCase {
    override fun create(parkingLotInfoVO: ParkingLotInfoVO) {
        val memberId = memberInquiryAdapter.findIdByMemberId(parkingLotInfoVO.memberId) ?: throw MemberException(
            MEMBER_NOT_FOUND,
            MEMBER_NOT_FOUND.message
        )

        parkingLotCommandAdapter.save(parkingLotInfoVO.toParkingLot(memberId))
    }

    override fun delete(parkingLotId: Long) {
        val parkingLot = parkingLotInquiryAdapter.findById(parkingLotId) ?: throw ParkingLotException(
            PARKING_LOT_NOT_FOUND,
            PARKING_LOT_NOT_FOUND.message
        )

        parkingLotCommandAdapter.update(parkingLot, LocalDateTime.now())
    }
}