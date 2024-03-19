package com.parking.api.application.service

import com.parking.api.adapter.out.MemberCommandAdapter
import com.parking.api.adapter.out.MemberInquiryAdapter
import com.parking.api.application.port.`in`.member.ModifyMemberInfoUseCase
import com.parking.api.application.port.`in`.member.RevokeMemberUseCase
import com.parking.api.application.port.`in`.member.SaveMemberUseCase
import com.parking.api.application.vo.MemberInfoVO
import com.parking.domain.entity.Member
import com.parking.domain.exception.MemberException
import com.parking.domain.exception.enum.ExceptionCode.*
import mu.KLogging
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberCommandService(
    private val memberInquiryAdapter: MemberInquiryAdapter,
    private val memberCommandAdapter: MemberCommandAdapter,
    private val passwordEncoder: PasswordEncoder
): SaveMemberUseCase, ModifyMemberInfoUseCase, RevokeMemberUseCase {

    override fun saveMember(memberInfoVO: MemberInfoVO, password: String): MemberInfoVO {
        val member = memberInfoVO.toMember()
        member.password = passwordEncoder.encode(password)
        val savedMember = saveMember(member)

        return MemberInfoVO.from(savedMember)
    }

    override fun modify(memberInfoVO: MemberInfoVO): MemberInfoVO {
        val member = findMember(memberInfoVO.memberId)

        member.modifyMemberInfo(memberInfoVO.memberName, memberInfoVO.memberEmail)

        val savedMember = saveMember(member)

        return MemberInfoVO.from(savedMember)
    }

    override fun revoke(memberId: String): MemberInfoVO {
        val member = findMember(memberId)

        member.revoke()

        if (!member.isRevoked()) throw MemberException(
            MEMBER_REVOKED_FAIL,
            MEMBER_REVOKED_FAIL.message
        )

        val savedMember = saveMember(member)

        return MemberInfoVO.from(savedMember)
    }

    private fun saveMember(member: Member): Member {
        return memberCommandAdapter.saveMember(member) ?: throw MemberException(
            MEMBER_SAVE_ERROR,
            MEMBER_SAVE_ERROR.message
        )
    }

    private fun findMember(memberId: String): Member {
        return memberInquiryAdapter.findMemberInfoByMemberId(memberId) ?: throw MemberException(
            MEMBER_NOT_FOUND,
            MEMBER_NOT_FOUND.message
        )
    }
    companion object : KLogging()
}