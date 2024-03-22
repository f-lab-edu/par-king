package com.parking.api.application.port.`in`.member

import com.parking.api.application.vo.MemberInfoVO

interface ModifyMemberInfoUseCase {
    fun modify(memberInfoVO: MemberInfoVO): MemberInfoVO
}