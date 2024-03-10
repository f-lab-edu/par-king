package api.application.service

import api.application.port.`in`.FindMemberUseCase
import api.application.port.`in`.RefreshAccessToken
import api.application.port.`in`.SignInMemberUseCase
import api.application.port.out.FindMemberPort
import api.application.vo.MemberInfoVO
import api.common.jwt.JwtTokenProvider
import api.common.jwt.Token
import api.util.PasswordUtil
import domain.entity.Member
import domain.exception.MemberException
import domain.exception.enum.ExceptionCode.*
import mu.KLogging
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import redis.service.RedisService
import java.time.LocalDateTime
import java.util.*

@Service
class MemberInquiryService(
    private val findMemberPort: FindMemberPort,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordUtil: PasswordUtil,
    private val redisService: RedisService<String>
) : FindMemberUseCase, SignInMemberUseCase, RefreshAccessToken {
    override fun findMemberInfoByMemberId(memberId: String): MemberInfoVO {
        val member = findMemberPort.findMemberInfoByMemberId(memberId) ?: throw MemberException(
            MEMBER_NOT_FOUND,
            MEMBER_NOT_FOUND.message
        )

        return MemberInfoVO.from(member)
    }

    override fun signIn(memberId: String, password: String): Token {
        // 인증 실패 횟수 조회
        if (!checkPasswordTryCount(memberId)) {
            throw MemberException(LOGIN_TRY_COUNT_LIMIT, LOGIN_TRY_COUNT_LIMIT.message)
        }

        try {
            // 인증시도
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(memberId, password, null)
            )
        } catch (e: Exception) {
            // 인증 실패
            throw MemberException(AUTHENTICATION_FAIL, AUTHENTICATION_FAIL.message)
        }

        val member = findMemberPort.findMemberInfoByMemberId(memberId)

        //비밀번호 일치 여부 확인
        if (!passwordUtil.matches(password, member?.password!!)) {
            val uuid = UUID.randomUUID()
            redisService.set("${memberId}_${uuid}", LocalDateTime.now().toString())

            throw MemberException(PASSWORD_NOT_MATCH, PASSWORD_NOT_MATCH.message)
        }

        // Login이 성공한 경우 Token 생성
        val accessToken = jwtTokenProvider.createAccessToken(memberId)
        val refreshToken = jwtTokenProvider.createRefreshToken(memberId)

        return Token(accessToken, refreshToken)
    }

    private fun checkPasswordTryCount(memberId: String) : Boolean {
        val values = redisService.getStringValues(String.format("%s_*", memberId))

        return Member.LIMIT_PASSWORD_TRY_COUNT >= values.size
    }

    override fun refreshAccessToken(refreshToken: String): Token {
        if (jwtTokenProvider.validateExpireToken(refreshToken)) {
            val memberId = jwtTokenProvider.parseUsername(refreshToken)

            return Token(
                accessToken = jwtTokenProvider.createAccessToken(memberId),
                refreshToken = refreshToken
            )
        }

        return Token()
    }

    companion object : KLogging()
}