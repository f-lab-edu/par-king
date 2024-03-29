package com.parking.api.application.service

import com.parking.api.application.port.`in`.parkingLot.FindParkingLotUseCase
import com.parking.api.application.port.out.FindMemberPort
import com.parking.api.application.port.out.FindParkingLotPort
import com.parking.api.application.vo.ParkingLotListInfoVO
import com.parking.api.application.vo.ParkingLotLocationVO
import com.parking.domain.exception.MemberException
import com.parking.domain.exception.enum.ExceptionCode.MEMBER_NOT_FOUND
import com.parking.domain.exception.enum.ExceptionCode.MEMBER_NOT_MATCH
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ParkingLotInquiryService(
    private val findMemberPort: FindMemberPort,
    private val findParkingLotPort: FindParkingLotPort
): FindParkingLotUseCase  {
    override fun findAllByMemberId(currentUserName: String, memberId: String, page: Pageable): Page<ParkingLotListInfoVO> {
        if (currentUserName.compareTo(memberId) != 0) {
            throw MemberException(
                MEMBER_NOT_MATCH,
                MEMBER_NOT_MATCH.message
            )
        }

        val foundMemberId = findMemberPort.findIdByMemberId(memberId) ?: throw MemberException(
            MEMBER_NOT_FOUND,
            MEMBER_NOT_FOUND.message
        )

        return findParkingLotPort.findAllByMemberId(foundMemberId, page).map { ParkingLotListInfoVO.from(it) }
    }

    override fun findAllByLocation(location: ParkingLotLocationVO, page: Pageable): Page<ParkingLotListInfoVO> {
        return findParkingLotPort.findAllByLocation(location, page).map { ParkingLotListInfoVO.from(it) }
    }
}