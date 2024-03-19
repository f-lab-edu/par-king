package com.parking.api.application.port.`in`.member

import com.parking.api.common.jwt.Token

interface RefreshAccessToken {
    fun refreshAccessToken(refreshToken: String): Token
}