package com.parking.api.application.service

import com.parking.api.adapter.out.MemberInquiryAdapter
import com.parking.api.adapter.out.ParkingLotCommandAdapter
import com.parking.api.application.port.`in`.CreateParkingLotUseCase
import com.parking.api.application.vo.ParkingLotInfoVO
import com.parking.domain.entity.ParkingLot
import com.parking.domain.exception.MemberException
import com.parking.domain.exception.enum.ExceptionCode
import org.springframework.stereotype.Service

@Service
class ParkingLotCommandService(
    private val memberInquiryAdapter: MemberInquiryAdapter,
    private val parkingLotCommandAdapter: ParkingLotCommandAdapter
): CreateParkingLotUseCase {
    override fun create(parkingLotInfoVO: ParkingLotInfoVO) {
        val memberId = memberInquiryAdapter.findIdByMemberId(parkingLotInfoVO.memberId) ?: throw MemberException(
            ExceptionCode.MEMBER_NOT_FOUND,
            ExceptionCode.MEMBER_NOT_FOUND.message
        )
        val deletedAt = ParkingLot.makeDeletedAt()

        parkingLotCommandAdapter.save(parkingLotInfoVO.toParkingLot(memberId, deletedAt))
    }
}