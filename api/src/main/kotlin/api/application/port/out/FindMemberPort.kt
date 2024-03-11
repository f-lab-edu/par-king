package api.application.port.out

import domain.entity.Member

interface FindMemberPort {
    fun findMemberInfoByMemberId(memberId: String) : Member?
}