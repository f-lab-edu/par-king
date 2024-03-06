package api.application.port.`in`

import api.common.jwt.Token

interface SignInMemberUseCase {
    fun signIn(memberId: String, password: String): Token
}