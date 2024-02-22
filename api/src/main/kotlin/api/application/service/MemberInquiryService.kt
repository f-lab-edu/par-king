package api.application.service

import api.application.port.`in`.FindMemberUseCase
import api.application.port.out.FindMemberPort
import api.application.vo.MemberInfoVO
import domain.exception.MemberException
import domain.exception.enum.ExceptionCode
import org.springframework.stereotype.Service

@Service
class MemberInquiryService(
    private val findMemberPort: FindMemberPort
) : FindMemberUseCase {
    override fun findMemberInfoByMemberId(memberId: Long): MemberInfoVO {
        val member = findMemberPort.findMemberInfoByMemberId(memberId) ?: throw MemberException(ExceptionCode.MEMBER_NOT_FOUND, ExceptionCode.MEMBER_NOT_FOUND.message)

        return MemberInfoVO.from(member)
    }
}