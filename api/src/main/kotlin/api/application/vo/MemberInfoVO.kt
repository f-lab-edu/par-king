package api.application.vo

import domain.entity.Member

data class MemberInfoVO(
    val memberId: String,
    val memberName: String,
    val memberEmail: String? = null
) {
    companion object {
        fun from(member: Member) : MemberInfoVO {
            val memberInfo = member.memberInfo

            val memberInfoVO = MemberInfoVO(
                memberId = memberInfo.memberId,
                memberName = memberInfo.name,
                memberEmail = memberInfo.email
            )

            return memberInfoVO
        }
    }
}
