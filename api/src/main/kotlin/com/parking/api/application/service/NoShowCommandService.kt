package com.parking.api.application.service

import com.parking.api.adapter.out.MemberCommandAdapter
import com.parking.api.adapter.out.MemberInquiryAdapter
import com.parking.api.adapter.out.NoShowCommandAdapter
import com.parking.api.application.port.`in`.noShow.SaveNoShowUseCase
import com.parking.domain.entity.NoShow
import com.parking.domain.exception.MemberException
import com.parking.domain.exception.enum.ExceptionCode.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class NoShowCommandService(
    private val memberInquiryAdapter: MemberInquiryAdapter,
    private val memberCommandAdapter: MemberCommandAdapter,
    private val noShowCommandAdapter: NoShowCommandAdapter
): SaveNoShowUseCase {

    @Transactional
    override fun save(memberId: String, parkingLotId: Long, carId: Long) {
        val member = memberInquiryAdapter.findMemberInfoByMemberId(memberId) ?: throw MemberException(
            MEMBER_NOT_FOUND,
            MEMBER_NOT_FOUND.message
        )

        member.makeNoShow()

        memberCommandAdapter.saveMember(member)

        val noShow =
            NoShow(memberId = member.id!!, parkingLotId = parkingLotId, carId = carId, noShowTime = LocalDateTime.now())

        noShowCommandAdapter.save(noShow)
    }
}