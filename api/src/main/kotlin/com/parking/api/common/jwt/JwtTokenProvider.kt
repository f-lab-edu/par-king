package com.parking.api.common.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.parking.domain.exception.enum.ExceptionCode
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class JwtTokenProvider(
    private val userDetailsService: UserDetailsService
) {
    @Value("\${jwt.secret}")
    private val secretKey: String = "SECRET_KEY"
    private val CLAIM_NAME = "username"

    // 토큰생성
    fun createAccessToken(username: String): String {
        val now = LocalDateTime.now()

        return JWT.create().withClaim(CLAIM_NAME, username).withExpiresAt(
            Date.from(
                now.plusMinutes(10L).atZone(ZoneId.systemDefault()).toInstant()
            )
        ).sign(Algorithm.HMAC256(secretKey))
    }

    fun createRefreshToken(username: String): String {
        val now = LocalDateTime.now()

        return JWT.create().withClaim(CLAIM_NAME, username).withExpiresAt(
            Date.from(
                now.plusDays(1L).atZone(ZoneId.systemDefault()).toInstant()
            )
        ).sign(Algorithm.HMAC256(secretKey))
    }

    // 토큰에서 username 파싱
    fun parseUsername(token: String): String {
        return JWT.decode(token).claims[CLAIM_NAME]?.asString()
            ?: throw Exception(ExceptionCode.TOKEN_PARSE_FAIL.message)
    }

    /**
     * 토큰이 expire 되었는지 확인한다
     * */
    fun validateExpireToken(jwtToken: String): Boolean {
        val jwtVerifier = JWT.require(Algorithm.HMAC256(secretKey)).acceptNotBefore(0L).build()
        try {
            jwtVerifier.verify(jwtToken)
        }
        catch (verificationEx: JWTVerificationException) {
            return false
        }
        return true
    }

    // JWT 토큰에서 인증 정보 조회
    fun getAuthentication(userName: String): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(userName)
        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }
}