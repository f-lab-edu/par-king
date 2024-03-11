package api.application.service

import api.adapter.out.MemberCommandAdapter
import api.application.port.`in`.SaveMemberUseCase
import api.application.vo.MemberInfoVO
import api.util.PasswordUtil
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