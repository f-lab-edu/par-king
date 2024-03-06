package api.application.port.`in`

import api.common.jwt.Token

interface MakeAccessToken {
    fun makeAccessToken(refreshToken: String): Token
}