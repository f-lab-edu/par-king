package com.parking.api.application.port.`in`

import com.parking.api.application.vo.MemberInfoVO

interface ModifyMemberInfoUseCase {
    fun modify(memberInfoVO: MemberInfoVO): MemberInfoVO
}