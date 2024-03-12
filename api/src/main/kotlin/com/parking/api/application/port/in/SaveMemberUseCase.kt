package com.parking.api.application.port.`in`

import com.parking.api.application.vo.MemberInfoVO

interface SaveMemberUseCase {
    fun saveMember(memberInfoVO: MemberInfoVO, password: String)
}