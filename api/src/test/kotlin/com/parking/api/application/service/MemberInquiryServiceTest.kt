package com.parking.api.application.service

import com.parking.api.application.port.out.FindMemberPort
import com.parking.api.application.vo.MemberInfoVO
import com.parking.api.common.jwt.JwtTokenProvider
import com.parking.api.common.jwt.Token
import com.parking.domain.entity.Member
import com.parking.domain.entity.MemberInfo
import com.parking.domain.exception.MemberException
import com.parking.redis.service.RedisService
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder

class MemberInquiryServiceTest : BehaviorSpec() {
    override fun isolationMode() = IsolationMode.SingleInstance

    init {
        val findMemberPort = mockk<FindMemberPort>()
        val authenticationManager = mockk<AuthenticationManager>()
        val jwtTokenProvider = mockk<JwtTokenProvider>()
        val passwordEncoder = mockk<PasswordEncoder>()
        val redisService = mockk<RedisService<String>>()

        val memberInquiryService =
            MemberInquiryService(findMemberPort, authenticationManager, jwtTokenProvider, passwordEncoder, redisService)

        Given("memberId 만 주어진 경우") {
            val memberId = "User1"

            When("memberId 가 db에 없을 떄") {

                every { findMemberPort.findMemberInfoByMemberId(any()) } returns null

                Then("Exception 발생") {
                    assertThrows<MemberException> {
                        memberInquiryService.findMemberInfoByMemberId(memberId)
                    }
                }
            }

            When("MemberInfo 를 조회해서 반환할 때") {
                val member = Member(id = 1L,
                    memberInfo = MemberInfo(memberId = "User1", name = "UserName", email = "User@User.com")
                )

                every { findMemberPort.findMemberInfoByMemberId(any()) } returns member

                Then("MemberInfo 가 반환돼야 한다.") {
                    val expectedResult = MemberInfoVO.from(member)
                    val realResult = memberInquiryService.findMemberInfoByMemberId(memberId)

                    Assertions.assertEquals(expectedResult, realResult)
                }
            }

            When("id를 조회하려는데 memberId 가 db에 없을 떄") {

                every { findMemberPort.findIdByMemberId(any()) } returns null

                Then("Exception 발생") {
                    assertThrows<MemberException> {
                        memberInquiryService.findIdByMemberId(memberId)
                    }
                }
            }

            When("id 를 조회해서 반환할 때") {
                val member = Member(id = 1L,
                    memberInfo = MemberInfo(memberId = "User1", name = "UserName", email = "User@User.com")
                )

                every { findMemberPort.findIdByMemberId(any()) } returns member.id

                Then("id 가 반환돼야 한다.") {
                    val expectedResult = member.id
                    val realResult = memberInquiryService.findIdByMemberId(memberId)

                    Assertions.assertEquals(expectedResult, realResult)
                }
            }
        }

        Given("refreshToken 이 주어진 경우") {
            val refreshToken = "Refresh Token"

            When("token 이 만료된 경우") {

                every { jwtTokenProvider.validateExpireToken(any()) } returns false

                Then("빈 토큰 반환") {
                    val realResult = memberInquiryService.refreshAccessToken(refreshToken)
                    val expectedResult = Token()

                    Assertions.assertEquals(expectedResult, realResult)
                }
            }

            When("token 이 만료되지 않은 경우") {
                val memberId = "User1"
                val accessToken = "Access Token"

                every { jwtTokenProvider.validateExpireToken(any()) } returns true
                every { jwtTokenProvider.parseUsername(any()) } returns memberId
                every { jwtTokenProvider.createAccessToken(any()) } returns accessToken

                Then("토큰 반환") {
                    val realResult = memberInquiryService.refreshAccessToken(refreshToken)
                    val expectedResult = Token(accessToken, refreshToken)

                    Assertions.assertEquals(expectedResult, realResult)
                }
            }
        }

        Given("id, password 가 주어진 경우") {
            val memberId = "user1"
            val password = "password"
            val memberTryStringList = mutableListOf<String>()
            val tryString = "user1 try password but fail"
            val member = Member(id = 1L, password = "password",
                memberInfo = MemberInfo(memberId = "User1", name = "UserName", email = "User@User.com")
            )

            When("인증 시도 횟수가 초과한 경우") {
                for(i in 1..Member.LIMIT_PASSWORD_TRY_COUNT+1) {
                    memberTryStringList.addLast(tryString)
                }

                every { redisService.getStringValues(any()) } returns memberTryStringList

                Then("Exception 발생") {
                    assertThrows<MemberException> {
                        memberInquiryService.signIn(memberId, password)
                    }
                }
            }

            When("Member 정보가 db에 없는 경우") {
                for(i in 1..<Member.LIMIT_PASSWORD_TRY_COUNT) {
                    memberTryStringList.addLast(tryString)
                }

                every { redisService.getStringValues(any()) } returns memberTryStringList
                every { findMemberPort.findMemberInfoByMemberId(any()) } returns null

                Then("Exception 발생") {
                    assertThrows<MemberException> {
                        memberInquiryService.signIn(memberId, password)
                    }
                }
            }

            When("비밀번호가 일치하지 않는 경우") {
                for(i in 1..<Member.LIMIT_PASSWORD_TRY_COUNT) {
                    memberTryStringList.addLast(tryString)
                }

                every { redisService.getStringValues(any()) } returns memberTryStringList
                every { findMemberPort.findMemberInfoByMemberId(any()) } returns member
                every { passwordEncoder.matches(any(), any()) } returns false
                every { redisService.set(any(), any()) } returns mockk()

                Then("Exception 발생") {
                    assertThrows<MemberException> {
                        memberInquiryService.signIn(memberId, password)
                    }
                }
            }

            When("비밀번호가 일치하지 않는 경우") {
                for(i in 1..<Member.LIMIT_PASSWORD_TRY_COUNT) {
                    memberTryStringList.addLast(tryString)
                }

                every { redisService.getStringValues(any()) } returns memberTryStringList
                every { findMemberPort.findMemberInfoByMemberId(any()) } returns member
                every { passwordEncoder.matches(any(), any()) } returns false
                every { redisService.set(any(), any()) } returns mockk()

                Then("Exception 발생") {
                    assertThrows<MemberException> {
                        memberInquiryService.signIn(memberId, password)
                    }
                }
            }

            When("인증에서 실패한 경우 경우") {
                for(i in 1..<Member.LIMIT_PASSWORD_TRY_COUNT) {
                    memberTryStringList.addLast(tryString)
                }

                every { redisService.getStringValues(any()) } returns memberTryStringList
                every { findMemberPort.findMemberInfoByMemberId(any()) } returns member
                every { passwordEncoder.matches(any(), any()) } returns true
                every { redisService.deleteStringValues(any()) } returns mockk()
                every { authenticationManager.authenticate(any()) } throws Exception()

                Then("Exception 발생") {
                    assertThrows<MemberException> {
                        memberInquiryService.signIn(memberId, password)
                    }
                }
            }

            When("signIn 셩공한 경우") {
                val accessToken = "accessToken"
                val refreshToken = "refreshToken"

                every { redisService.getStringValues(any()) } returns listOf()
                every { findMemberPort.findMemberInfoByMemberId(any()) } returns member
                every { passwordEncoder.matches(any(), any()) } returns true
                every { redisService.deleteStringValues(any()) } returns mockk()
                every { authenticationManager.authenticate(any()) } returns mockk()
                every { jwtTokenProvider.createAccessToken(any()) } returns accessToken
                every { jwtTokenProvider.createRefreshToken(any()) } returns refreshToken

                Then("토큰 발행") {
                    val realResult = memberInquiryService.signIn(memberId, password)
                    val expectedResult = Token(accessToken, refreshToken)

                    Assertions.assertEquals(expectedResult, realResult)
                }
            }
        }
    }
}