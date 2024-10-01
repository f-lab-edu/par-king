package com.parking.api.adapter.out

import com.parking.api.application.port.out.FindMemberPort
import com.parking.domain.entity.Member
import com.parking.jpa.repositories.MemberRepository
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrNull

@Component
class MemberInquiryAdapter(
    private val memberRepository: MemberRepository
) : FindMemberPort {
    override fun findMemberInfoByMemberId(memberId: String): Member? {
        return memberRepository.findMemberByMemberId(memberId)?.to()
    }

    override fun findIdByMemberId(memberId: String): Long? {
        return memberRepository.findIdByMemberId(memberId)
    }

    override fun findById(memberId: Long): Member? {
        return memberRepository.findById(memberId).getOrNull()?.to()
    }
}