package api.adapter.out

import api.application.port.out.SaveMemberPort
import domain.entity.Member
import jpa.entity.MemberJpaEntity
import jpa.repositories.MemberJpaRepository
import org.springframework.stereotype.Component

@Component
class MemberCommandAdapter(
    private val memberJpaRepository: MemberJpaRepository
): SaveMemberPort {
    override fun saveMember(member: Member) : Member {
        return memberJpaRepository.save(MemberJpaEntity.from(member)).to()
    }
}