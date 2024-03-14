package com.parking.api.common.jwt

import com.parking.domain.exception.enum.ExceptionCode
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter


const val SIGN_IN_URL = "/member/sign-in"
const val BEARER_TOKEN = "Bearer "

class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 헤더에 Authorization이 있다면 가져온다.
        val authorizationHeader = request.getHeader("Authorization") ?: return filterChain.doFilter(request, response)
        if (request.requestURI ==  SIGN_IN_URL)
            return filterChain.doFilter(request, response)

        // Bearer타입 토큰이 있을 때 가져온다.
        val token = authorizationHeader.substring(BEARER_TOKEN.length)

        // 레디스에 토큰 정보가 있는지 확인 후 토큰 검증
        if (jwtTokenProvider.validateExpireToken(token)) {
            val username = jwtTokenProvider.parseUsername(token)
            // username으로 AuthenticationToken 생성
            val authentication: Authentication = jwtTokenProvider.getAuthentication(username)
            // SecurityContext에 등록
            SecurityContextHolder.getContext().authentication = authentication
        } else {
            // 토큰이 만료가 되면 401 응답을 보낸다
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            request.setAttribute("exception", Exception(ExceptionCode.TOKEN_EXPIRED_ERROR.message))
        }

        filterChain.doFilter(request, response)
    }
}