package com.parking.api.application.port.`in`

import com.parking.api.common.jwt.Token

interface RefreshAccessToken {
    fun refreshAccessToken(refreshToken: String): Token
}