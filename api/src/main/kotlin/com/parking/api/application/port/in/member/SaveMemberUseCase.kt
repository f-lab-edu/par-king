package com.parking.api.application.port.`in`.member

import com.parking.api.application.vo.MemberInfoVO

interface SaveMemberUseCase {
    fun saveMember(memberInfoVO: MemberInfoVO, password: String): MemberInfoVO
}