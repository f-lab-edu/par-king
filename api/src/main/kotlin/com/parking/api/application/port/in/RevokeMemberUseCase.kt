package com.parking.api.application.port.`in`

interface RevokeMemberUseCase {
    fun revoke(memberId: String): Boolean
}