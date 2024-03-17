package com.parking.api.application.service

import com.parking.api.adapter.out.MemberCommandAdapter
import com.parking.api.adapter.out.MemberInquiryAdapter
import com.parking.api.application.port.`in`.ModifyMemberInfoUseCase
import com.parking.api.application.port.`in`.RevokeMemberUseCase
import com.parking.api.application.port.`in`.SaveMemberUseCase
import com.parking.api.application.vo.MemberInfoVO
import com.parking.domain.entity.Member
import com.parking.domain.exception.MemberException
import com.parking.domain.exception.enum.ExceptionCode
import mu.KLogging
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberCommandService(
    private val memberInquiryAdapter: MemberInquiryAdapter,
    private val memberCommandAdapter: MemberCommandAdapter,
    private val passwordEncoder: PasswordEncoder
): SaveMemberUseCase, ModifyMemberInfoUseCase, RevokeMemberUseCase {

    override fun saveMember(memberInfoVO: MemberInfoVO, password: String) {
        val member = memberInfoVO.toMember()
        member.password = passwordEncoder.encode(password)

        memberCommandAdapter.saveMember(member)
    }

    override fun modify(memberInfoVO: MemberInfoVO): MemberInfoVO {
        val member = findMember(memberInfoVO.memberId)

        member.modifyMemberInfo(memberInfoVO.memberName, memberInfoVO.memberEmail)

        memberCommandAdapter.saveMember(member)

        return MemberInfoVO.from(member)
    }

    override fun revoke(memberId: String) {
        val member = findMember(memberId)

        member.revoke()

        memberCommandAdapter.saveMember(member)
    }

    private fun findMember(memberId: String): Member {
        return memberInquiryAdapter.findMemberInfoByMemberId(memberId) ?: throw MemberException(
            ExceptionCode.MEMBER_NOT_FOUND,
            ExceptionCode.MEMBER_NOT_FOUND.message
        )
    }
    companion object : KLogging()
}