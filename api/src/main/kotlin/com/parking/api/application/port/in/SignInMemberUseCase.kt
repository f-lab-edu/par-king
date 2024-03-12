package com.parking.api.application.port.`in`

import com.parking.api.common.jwt.Token

interface SignInMemberUseCase {
    fun signIn(memberId: String, password: String): Token
}