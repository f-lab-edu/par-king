package com.parking.api.adapter.`in`.dto

import com.parking.api.application.vo.MemberInfoVO

data class MemberInfoDTO(
    val memberId: String,
    val memberName: String,
    val memberEmail: String? = null
) {
    fun to() = MemberInfoVO(
        memberId = memberId,
        memberName = memberName,
        memberEmail = memberEmail
    )
}
