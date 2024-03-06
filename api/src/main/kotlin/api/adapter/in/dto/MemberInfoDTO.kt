package api.adapter.`in`.dto

import api.application.vo.MemberInfoVO

data class MemberInfoDTO(
    val memberId: String,
    val password: String? = null,
    val memberName: String,
    val memberEmail: String? = null
) {
    fun toVO() = MemberInfoVO(
        memberId = memberId,
        memberName = memberName,
        memberEmail = memberEmail
    )
}
