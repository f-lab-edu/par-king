package api.application.port.`in`

import api.application.vo.MemberInfoVO

interface FindMemberUseCase {
    fun findMemberInfoByMemberId(memberId: Long) : MemberInfoVO
}