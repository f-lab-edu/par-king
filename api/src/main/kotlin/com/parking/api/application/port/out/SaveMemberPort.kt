package com.parking.api.application.port.out

import domain.entity.Member

interface SaveMemberPort {
    fun saveMember(member: Member) : Member
}