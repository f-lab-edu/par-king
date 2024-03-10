package api.application.port.`in`

import api.common.jwt.Token

interface CreateTokenUsingRefreshTokenUseCase {
    fun createAccessToken(refreshToken: String): Token
}