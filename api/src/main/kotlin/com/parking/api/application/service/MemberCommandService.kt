package com.parking.api.application.service

import com.parking.api.adapter.out.MemberCommandAdapter
import com.parking.api.application.port.`in`.SaveMemberUseCase
import com.parking.api.application.vo.MemberInfoVO
import com.parking.api.util.PasswordUtil
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberCommandService(
    private val memberCommandAdapter: MemberCommandAdapter,
    private val passwordUtil: PasswordUtil
): SaveMemberUseCase {

    @Transactional
    override fun saveMember(memberInfoVO: MemberInfoVO, password: String) {
        val member = memberInfoVO.toMember()
        member.password = passwordUtil.encode(password)

        val savedMember = memberCommandAdapter.saveMember(member)
        logger.info (savedMember.getMemberId())
    }

    companion object : KLogging()
}