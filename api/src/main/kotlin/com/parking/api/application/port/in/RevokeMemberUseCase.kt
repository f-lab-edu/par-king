package com.parking.api.application.port.`in`

import com.parking.api.application.vo.MemberInfoVO

interface RevokeMemberUseCase {
    fun revoke(memberId: String): MemberInfoVO
}