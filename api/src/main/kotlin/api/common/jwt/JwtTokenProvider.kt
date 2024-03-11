package api.common.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*

@Component
class JwtTokenProvider(
    private val userDetailsService: UserDetailsService
) {
    @Value("\${jwt.secret}")
    private val secretKey: String = ""
    private val ACCESS_TOKEN_EXPIRE_TIME: Long = 10 * 60 * 1000L
    private val REFRESH_TOKEN_EXPIRE_TIME: Long = 24 * 60 * 60 * 1000L

    private fun generateKey(): Key {
        return Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
    }

    // 토큰생성
    fun createAccessToken(username: String): String {
        val claims: Claims = Jwts.claims()
        claims["username"] = username

        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME))
            .signWith(generateKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    fun createRefreshToken(username: String): String {
        val claims: Claims = Jwts.claims()
        claims["username"] = username

        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME))
            .signWith(generateKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    // 토큰에서 username 파싱
    fun parseUsername(token: String): String {
        val claims: Claims = getClaimByToken(token)
        return claims["username"] as String
    }

    // 토큰에서 claim 부분 가져옴
    fun getClaimByToken(token: String) : Claims {
        return Jwts.parserBuilder()
            .setSigningKey(generateKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    /**
     * 토큰이 expire 되었는지 확인한다
     * */
    fun validateExpireToken(jwtToken: String): Boolean {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(jwtToken)
            !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

    // JWT 토큰에서 인증 정보 조회
    fun getAuthentication(userName: String): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(userName)
        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }
}