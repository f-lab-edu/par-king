package com.parking.api.application.vo

import com.parking.domain.entity.Member
import com.parking.domain.entity.MemberInfo

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

    fun toMember() = Member(
        memberInfo = MemberInfo(memberId = this.memberId, name = this.memberName, email = this.memberEmail)
    )
}
