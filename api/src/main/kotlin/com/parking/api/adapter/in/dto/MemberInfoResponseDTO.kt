package com.parking.api.adapter.`in`.dto

import com.parking.api.application.vo.MemberInfoVO

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
