package com.parking.api.adapter.out

import com.parking.api.application.port.out.SaveMemberPort
import com.parking.domain.entity.Member
import com.parking.jpa.entity.MemberEntity
import com.parking.jpa.repositories.MemberRepository
import org.springframework.stereotype.Component

@Component
class MemberCommandAdapter(
    private val memberRepository: MemberRepository
): SaveMemberPort {
    override fun saveMember(member: Member): Member? {
        return memberRepository.save(MemberEntity.from(member)).to()
    }
}