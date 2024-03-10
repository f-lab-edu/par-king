package api.adapter.`in`.dto

import api.application.vo.MemberInfoVO

data class SignUpDTO(
    val memberId: String,
    val password: String,
    val memberName: String,
    val memberEmail: String? = null
) {
    fun toMemberInfoVO() = MemberInfoVO(
        memberId = memberId,
        memberName = memberName,
        memberEmail = memberEmail
    )
}
