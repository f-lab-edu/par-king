package com.parking.api.application.service

import com.parking.api.application.port.`in`.member.ModifyMemberInfoUseCase
import com.parking.api.application.port.`in`.member.RevokeMemberUseCase
import com.parking.api.application.port.`in`.member.SaveMemberUseCase
import com.parking.api.application.port.out.FindMemberPort
import com.parking.api.application.port.out.SaveMemberPort
import com.parking.api.application.vo.MemberInfoVO
import com.parking.domain.entity.Member
import com.parking.domain.exception.MemberException
import com.parking.domain.exception.enum.ExceptionCode.*
import mu.KLogging
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberCommandService(
    private val findMemberPort: FindMemberPort,
    private val saveMemberPort: SaveMemberPort,
    private val passwordEncoder: PasswordEncoder
): SaveMemberUseCase, ModifyMemberInfoUseCase, RevokeMemberUseCase {

    @Transactional
    override fun saveMember(memberInfoVO: MemberInfoVO, password: String): MemberInfoVO {
        val member = memberInfoVO.toMember()
        member.password = passwordEncoder.encode(password)
        val savedMember = saveMember(member)

        return MemberInfoVO.from(savedMember)
    }

    @Transactional
    override fun modify(memberInfoVO: MemberInfoVO): MemberInfoVO {
        val member = findMember(memberInfoVO.memberId)

        member.modifyMemberInfo(memberInfoVO.memberName, memberInfoVO.memberEmail)

        val savedMember = saveMember(member)

        return MemberInfoVO.from(savedMember)
    }

    @Transactional
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
        return saveMemberPort.saveMember(member) ?: throw MemberException(
            MEMBER_SAVE_ERROR,
            MEMBER_SAVE_ERROR.message
        )
    }

    private fun findMember(memberId: String): Member {
        return findMemberPort.findMemberInfoByMemberId(memberId) ?: throw MemberException(
            MEMBER_NOT_FOUND,
            MEMBER_NOT_FOUND.message
        )
    }
    companion object : KLogging()
}