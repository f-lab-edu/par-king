package com.parking.api.application.service

import com.parking.api.adapter.out.MemberCommandAdapter
import com.parking.api.adapter.out.MemberInquiryAdapter
import com.parking.api.application.port.`in`.ModifyMemberInfoUseCase
import com.parking.api.application.port.`in`.SaveMemberUseCase
import com.parking.api.application.vo.MemberInfoVO
import com.parking.domain.exception.MemberException
import com.parking.domain.exception.enum.ExceptionCode
import mu.KLogging
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberCommandService(
    private val memberInquiryAdapter: MemberInquiryAdapter,
    private val memberCommandAdapter: MemberCommandAdapter,
    private val passwordEncoder: PasswordEncoder
): SaveMemberUseCase, ModifyMemberInfoUseCase {

    @Transactional
    override fun saveMember(memberInfoVO: MemberInfoVO, password: String) {
        val member = memberInfoVO.toMember()
        member.password = passwordEncoder.encode(password)

        val savedMember = memberCommandAdapter.saveMember(member)
        logger.info (savedMember.getMemberId())
    }

    override fun modify(memberInfoVO: MemberInfoVO): MemberInfoVO {
        val member = memberInquiryAdapter.findMemberInfoByMemberId(memberInfoVO.memberId) ?: throw MemberException(
            ExceptionCode.MEMBER_NOT_FOUND,
            ExceptionCode.MEMBER_NOT_FOUND.message
        )

        member.modifyMemberInfo(memberInfoVO.memberName, memberInfoVO.memberEmail)

        val savedMember = memberCommandAdapter.saveMember(member)

        return MemberInfoVO.from(savedMember)
    }
    companion object : KLogging()
}