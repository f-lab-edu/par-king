package com.parking.api.application.port.out

import com.parking.domain.entity.Member

interface FindMemberPort {
    fun findMemberInfoByMemberId(memberId: String) : Member?

    fun findIdByMemberId(memberId: String): Long?
}