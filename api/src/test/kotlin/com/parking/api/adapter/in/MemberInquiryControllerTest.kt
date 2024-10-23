package com.parking.api.adapter.`in`

import com.fasterxml.jackson.databind.ObjectMapper
import com.parking.api.adapter.`in`.dto.MemberInfoResponseDTO
import com.parking.api.application.port.`in`.member.FindMemberUseCase
import com.parking.api.application.port.`in`.member.RefreshAccessToken
import com.parking.api.application.port.`in`.member.SignInMemberUseCase
import com.parking.api.application.vo.MemberInfoVO
import com.parking.api.common.advice.ParkingAdvice
import com.parking.api.common.dto.SuccessResponseDTO
import com.parking.api.common.jwt.Token
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
@ActiveProfiles(profiles = ["local","persistence-local"])
class MemberInquiryControllerTest(
    private val objectMapper: ObjectMapper
) : DescribeSpec() {

    init {
        lateinit var mockMvc: MockMvc
        val findMemberUseCase =  mockk<FindMemberUseCase>()
        val signInMemberUseCase =  mockk<SignInMemberUseCase>()
        val refreshAccessToken =  mockk<RefreshAccessToken>()

        this.beforeTest {
            mockMvc = MockMvcBuilders.standaloneSetup(
                MemberInquiryController(findMemberUseCase, signInMemberUseCase, refreshAccessToken)
            )
                .setControllerAdvice(
                    ParkingAdvice::class.java
                )
                .build()
        }

        this.describe("MemberInquiryController Test") {
            context("멤버 정보를 가져오는 경우") {
                val memberId = "memberId"
                val memberInfo = MemberInfoVO(
                    "memberId",
                    "name",
                    "email@email.com"
                )

                every { findMemberUseCase.findMemberInfoByMemberId(any()) } returns memberInfo

                it("멤버 정보를 반환해야 한다.") {
                    val result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/member/info?memberId=$memberId")
                    )
                            .andDo(MockMvcResultHandlers.print())
                            .andExpect(MockMvcResultMatchers.status().isOk)
                            .andReturn()

                    val responseBody =
                        objectMapper.readValue(result.response.contentAsByteArray, SuccessResponseDTO::class.java)


                    val realResult =
                        objectMapper.convertValue(responseBody.content, MemberInfoResponseDTO::class.java)

                    val expectedResult = MemberInfoResponseDTO.from(memberInfo)

                    realResult shouldBe expectedResult
                }
            }

            context("refresh token 을 가져오는 경우") {
                val token = Token(
                    "accessToken",
                    "refreshToken"
                )
                val outToken = Token(
                    "outAccessToken",
                    "outRefreshToken"
                )

                every { refreshAccessToken.refreshAccessToken(any()) } returns outToken

                it("토큰 정보를 반환해야 한다.") {
                    val result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/member/refresh")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(token))
                    )
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()

                    val responseBody =
                        objectMapper.readValue(result.response.contentAsByteArray, SuccessResponseDTO::class.java)


                    val realResult =
                        objectMapper.convertValue(responseBody.content, Token::class.java)

                    realResult shouldBe outToken
                }
            }
        }
    }
}
