package com.parking.api.adapter.`in`

import com.fasterxml.jackson.databind.ObjectMapper
import com.parking.api.adapter.`in`.dto.MemberInfoDTO
import com.parking.api.adapter.`in`.dto.MemberInfoResponseDTO
import com.parking.api.adapter.`in`.dto.SignUpDTO
import com.parking.api.application.port.`in`.member.ModifyMemberInfoUseCase
import com.parking.api.application.port.`in`.member.RevokeMemberUseCase
import com.parking.api.application.port.`in`.member.SaveMemberUseCase
import com.parking.api.application.vo.MemberInfoVO
import com.parking.api.common.advice.ParkingAdvice
import com.parking.api.common.dto.SuccessResponseDTO
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
class MemberCommandControllerTest(
    private val mapper: ObjectMapper
) : DescribeSpec() {
    init {
        lateinit var mockMvc: MockMvc
        val saveMemberUseCase = mockk<SaveMemberUseCase>()
        val modifyMemberInfoUseCase = mockk<ModifyMemberInfoUseCase>()
        val revokeMemberUseCase = mockk<RevokeMemberUseCase>()

        this.beforeTest {
            mockMvc = MockMvcBuilders.standaloneSetup(
                MemberCommandController(saveMemberUseCase, modifyMemberInfoUseCase, revokeMemberUseCase)
            )
                .setControllerAdvice(
                    ParkingAdvice::class.java
                )
                .build()
        }

        this.describe("MemberCommandController Test") {
            context("가입하는 경우") {
                val memberId = "memberId"
                val memberInfo = MemberInfoVO(
                    memberId = memberId,
                    memberName = "memberName",
                    memberEmail = "email@email"
                )
                val signUpDTO = SignUpDTO(
                    memberId = memberId,
                    memberName = "memberName",
                    memberEmail = "email@email",
                    password = "password"

                )

                every { saveMemberUseCase.saveMember(any(), any()) } returns memberInfo

                it("멤버 정보를 반환해야 한다.") {
                    val expectedResult = MemberInfoResponseDTO(
                        memberInfo.memberId, memberInfo.memberName, memberInfo.memberEmail
                    )

                    val result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/member/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(signUpDTO))
                        )
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()

                    val responseBody =
                        mapper.readValue(result.response.contentAsByteArray, SuccessResponseDTO::class.java)


                    val realResult =
                        mapper.convertValue(responseBody.content, MemberInfoResponseDTO::class.java)

                    realResult shouldBe expectedResult
                }
            }
            context("회원 정보 수정하는 경우") {
                val memberInfoDTO = MemberInfoDTO(
                    memberId = "memberId",
                    memberName = "memberName2",
                    memberEmail = "email@email"
                )
                val memberInfo = MemberInfoVO(
                    memberId = "memberId",
                    memberName = "memberName2",
                    memberEmail = "email@email"
                )

                every { modifyMemberInfoUseCase.modify(any()) } returns memberInfo

                it("수정된 멤버 정보를 반환해야 한다.") {
                    val expectedResult = MemberInfoResponseDTO(
                        memberInfo.memberId, memberInfo.memberName, memberInfo.memberEmail
                    )

                    val result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/member/modify")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(memberInfoDTO))
                    )
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()

                    val responseBody =
                        mapper.readValue(result.response.contentAsByteArray, SuccessResponseDTO::class.java)


                    val realResult =
                        mapper.convertValue(responseBody.content, MemberInfoResponseDTO::class.java)

                    realResult shouldBe expectedResult
                }
            }
            context("탈퇴하는 경우") {
                val memberId = "1"

                every { revokeMemberUseCase.revoke(any()) } returns mockk()

                it("탈퇴 후 성공을 리턴해야 한다.") {

                    val result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/member/revoke?memberId=${memberId}")
                    )
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()

                    val responseBody =
                        mapper.readValue(result.response.contentAsByteArray, SuccessResponseDTO::class.java)

                    responseBody.content shouldBe true
                }
            }
        }
    }
}