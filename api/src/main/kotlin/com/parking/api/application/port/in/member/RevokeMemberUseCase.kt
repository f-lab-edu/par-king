package com.parking.api.application.port.`in`.member

import com.parking.api.application.vo.MemberInfoVO

interface RevokeMemberUseCase {
    fun revoke(memberId: String): MemberInfoVO
}