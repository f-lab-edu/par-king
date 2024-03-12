package com.parking.api.application.port.out

import com.parking.domain.entity.Member

interface SaveMemberPort {
    fun saveMember(member: Member) : Member
}