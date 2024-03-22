package com.parking.api.application.service

import com.parking.api.adapter.out.MemberInquiryAdapter
import com.parking.api.adapter.out.ParkingLotInquiryAdapter
import com.parking.api.application.port.`in`.parkingLot.FindParkingLotUseCase
import com.parking.api.application.vo.ParkingLotListInfoVO
import com.parking.domain.exception.MemberException
import com.parking.domain.exception.enum.ExceptionCode.MEMBER_NOT_FOUND
import com.parking.domain.exception.enum.ExceptionCode.MEMBER_NOT_MATCH
import org.springframework.stereotype.Service

@Service
class ParkingLotInquiryService(
    private val memberInquiryAdapter: MemberInquiryAdapter,
    private val parkingLotInquiryAdapter: ParkingLotInquiryAdapter
): FindParkingLotUseCase  {
    override fun findAllByMemberId(currentUserName: String, memberId: String): List<ParkingLotListInfoVO> {
        if (currentUserName.compareTo(memberId) != 0) {
            throw MemberException(
                MEMBER_NOT_MATCH,
                MEMBER_NOT_MATCH.message
            )
        }

        val foundMemberId = memberInquiryAdapter.findIdByMemberId(memberId) ?: throw MemberException(
            MEMBER_NOT_FOUND,
            MEMBER_NOT_FOUND.message
        )

        return parkingLotInquiryAdapter.findByMemberId(foundMemberId).map { ParkingLotListInfoVO.from(it) }
    }
}