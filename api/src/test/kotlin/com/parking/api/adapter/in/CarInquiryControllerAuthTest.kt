package com.parking.api.adapter.`in`


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.parking.api.adapter.common.StringUtil.toObjectList
import com.parking.api.adapter.common.WithMockUser
import com.parking.api.adapter.`in`.dto.ResponseCarInfoDTO
import com.parking.api.application.port.`in`.car.FindCarUseCase
import com.parking.api.application.vo.ResponseCarInfoVO
import com.parking.api.common.advice.ParkingAdvice
import com.parking.api.common.dto.SuccessResponseDTO
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
@ActiveProfiles(profiles = ["local","persistence-local"])
class CarInquiryControllerAuthTest {

    val findCarUseCase = mockk<FindCarUseCase>()
    lateinit var mockMvcCustom: MockMvc
    lateinit var objectMapper: ObjectMapper


    @BeforeEach
    fun beforeTest() {
        mockMvcCustom = MockMvcBuilders.standaloneSetup(
                CarInquiryController(findCarUseCase)
            )
            .setControllerAdvice(
                ParkingAdvice::class.java
            )
            .setCustomArgumentResolvers(AuthenticationPrincipalArgumentResolver())
            .build()

        objectMapper = ObjectMapper().registerKotlinModule()
    }

    @Test
    @WithMockUser
    fun carInquiryAuthTest() {
        val responseCarInfo = ResponseCarInfoVO(
            1L,
            "가1234"
        )

        every { findCarUseCase.findAllByMemberId(any(), any()) } returns listOf(responseCarInfo, responseCarInfo)

        val result = mockMvcCustom.perform(
                MockMvcRequestBuilders.get("/car/find/member?memberId=User1")
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()


        val responseBody =
            objectMapper.readValue(result.response.contentAsByteArray, SuccessResponseDTO::class.java)

        val responseList = objectMapper.writeValueAsString(responseBody.content).toObjectList<ResponseCarInfoDTO>()

        responseList.forEach { it ->
            it.carNumber shouldBe responseCarInfo.carNumber
        }
    }
}