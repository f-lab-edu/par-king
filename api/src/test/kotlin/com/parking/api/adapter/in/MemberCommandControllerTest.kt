package com.parking.api.adapter.`in`

import com.fasterxml.jackson.databind.ObjectMapper
import com.parking.api.application.port.`in`.member.ModifyMemberInfoUseCase
import com.parking.api.application.port.`in`.member.RevokeMemberUseCase
import com.parking.api.application.port.`in`.member.SaveMemberUseCase
import com.parking.api.common.advice.ParkingAdvice
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.mockk
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
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

            }
            context("회원 정보 수정하는 경우") {

            }
            context("탈퇴하는 경우") {

            }
        }
    }
}