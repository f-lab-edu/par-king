package com.parking.api.application.port.`in`

import com.parking.api.application.vo.MemberInfoVO

interface FindMemberUseCase {
    fun findMemberInfoByMemberId(memberId: String) : MemberInfoVO
}