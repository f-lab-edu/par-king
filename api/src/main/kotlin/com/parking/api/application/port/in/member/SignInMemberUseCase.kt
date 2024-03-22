package com.parking.api.application.port.`in`.member

import com.parking.api.common.jwt.Token

interface SignInMemberUseCase {
    fun signIn(memberId: String, password: String): Token
}