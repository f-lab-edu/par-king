package api.adapter.out

import api.application.port.out.FindMemberPort
import domain.entity.Member
import jpa.repositories.MemberJpaRepository
import org.springframework.stereotype.Component

@Component
class MemberInquiryAdapter(
    private val memberJpaRepository: MemberJpaRepository
) : FindMemberPort {
    override fun findMemberInfoByMemberId(memberId: Long): Member? {
        return memberJpaRepository.findMemberByMemberId(memberId)?.to()
    }
}