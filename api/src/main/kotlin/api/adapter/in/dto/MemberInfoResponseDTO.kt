package api.adapter.`in`.dto

import api.application.vo.MemberInfoVO

data class MemberInfoResponseDTO(
    val memberId: String,
    val memberName: String,
    val memberEmail: String? = null
) {
    companion object {
        fun from(memberVO: MemberInfoVO) = MemberInfoResponseDTO(
            memberId = memberVO.memberId,
            memberName = memberVO.memberName,
            memberEmail = memberVO.memberEmail
        )
    }
}
