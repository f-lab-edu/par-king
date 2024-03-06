package api.application.port.`in`

import api.application.vo.MemberInfoVO

interface SaveMemberUseCase {
    fun saveMember(memberInfoVO: MemberInfoVO, password: String)
}