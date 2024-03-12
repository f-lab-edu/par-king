package com.parking.api.common.jwt

data class Token(
    val accessToken: String? = null,
    val refreshToken: String? = null
)
