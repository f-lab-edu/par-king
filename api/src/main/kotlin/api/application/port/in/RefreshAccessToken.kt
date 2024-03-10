package api.application.port.`in`

import api.common.jwt.Token

interface RefreshAccessToken {
    fun refreshAccessToken(refreshToken: String): Token
}