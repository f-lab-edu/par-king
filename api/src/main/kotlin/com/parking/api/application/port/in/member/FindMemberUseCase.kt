package com.parking.api.application.port.`in`.member

import com.parking.api.application.vo.MemberInfoVO

interface FindMemberUseCase {
    fun findMemberInfoByMemberId(memberId: String) : MemberInfoVO

    fun findIdByMemberId(memberId: String): Long
}