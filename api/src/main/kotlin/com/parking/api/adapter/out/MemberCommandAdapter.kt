package com.parking.api.adapter.out

import com.parking.api.application.port.out.SaveMemberPort
import com.parking.domain.entity.Member
import com.parking.jpa.entity.MemberJpaEntity
import com.parking.jpa.repositories.MemberJpaRepository
import org.springframework.stereotype.Component

@Component
class MemberCommandAdapter(
    private val memberJpaRepository: MemberJpaRepository
): SaveMemberPort {
    override fun saveMember(member: Member) {
        memberJpaRepository.save(MemberJpaEntity.from(member))
    }
}