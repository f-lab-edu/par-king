package com.parking.api.adapter.out

import com.parking.api.application.port.out.FindMemberPort
import com.parking.domain.entity.Member
import com.parking.jpa.repositories.MemberJpaRepository
import org.springframework.stereotype.Component

@Component
class MemberInquiryAdapter(
    private val memberJpaRepository: MemberJpaRepository
) : FindMemberPort {
    override fun findMemberInfoByMemberId(memberId: String): Member? {
        return memberJpaRepository.findMemberByMemberId(memberId)?.to()
    }

    override fun findIdByMemberId(memberId: String): Long? {
        return memberJpaRepository.findIdByMemberId(memberId)
    }
}