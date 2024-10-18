package com.parking.api.adapter.`in`

import com.fasterxml.jackson.databind.ObjectMapper
import com.parking.api.application.port.`in`.member.FindMemberUseCase
import com.parking.api.application.port.`in`.member.RefreshAccessToken
import com.parking.api.application.port.`in`.member.SignInMemberUseCase
import com.parking.api.common.advice.ParkingAdvice
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.mockk
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
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


    }
}
