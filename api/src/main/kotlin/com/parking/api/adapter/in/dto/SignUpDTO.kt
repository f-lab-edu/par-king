package com.parking.api.adapter.`in`.dto

import com.parking.api.application.vo.MemberInfoVO

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
