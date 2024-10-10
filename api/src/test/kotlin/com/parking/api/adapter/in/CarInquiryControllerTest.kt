package com.parking.api.adapter.`in`

import com.fasterxml.jackson.databind.ObjectMapper
import com.parking.api.adapter.`in`.dto.ResponseCarInfoDTO
import com.parking.api.application.port.`in`.car.FindCarUseCase
import com.parking.api.application.vo.ResponseCarInfoVO
import com.parking.api.common.advice.ParkingAdvice
import com.parking.api.common.dto.ExceptionResponseDTO
import com.parking.api.common.dto.SuccessResponseDTO
import com.parking.domain.exception.CarException
import com.parking.domain.exception.enum.ExceptionCode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
@ActiveProfiles(profiles = ["local","persistence-local"])
class CarInquiryControllerTest(
    private val objectMapper: ObjectMapper
) : DescribeSpec() {
    init {
        lateinit var mockMvc: MockMvc
        val findCarUseCase = mockk<FindCarUseCase>()

        this.beforeTest {
            mockMvc = MockMvcBuilders.standaloneSetup(
                    CarInquiryController(findCarUseCase)
                )
                .setControllerAdvice(
                    ParkingAdvice::class.java
                )
                .build()
        }

        this.describe("CarInquiryController Test") {
            context("carId로 찾는 경우") {
                val carId = 1L

                it("carId로 찾았을 때") {
                    val responseCarInfo = ResponseCarInfoVO(
                        1L,
                        "가1234"
                    )

                    every { findCarUseCase.findById(any()) } returns responseCarInfo

                    val result = mockMvc.perform(
                        get("/car/find/$carId")

                        )
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()

                    val responseBody =
                        objectMapper.readValue(result.response.contentAsByteArray, SuccessResponseDTO::class.java)

                    val responseContent: ResponseCarInfoDTO =
                        objectMapper.convertValue(responseBody.content, ResponseCarInfoDTO::class.java)

                    responseContent.carNumber shouldBe responseCarInfo.carNumber
                    result.response.status shouldBe HttpStatus.OK.value()
                }

                it("carId로 못찾았을 때") {

                    every { findCarUseCase.findById(any()) } throws CarException(
                        ExceptionCode.CAR_NOT_FOUND,
                        ExceptionCode.CAR_NOT_FOUND.message
                    )

                    val result = mockMvc.perform(
                        get("/car/find/$carId")

                    )
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()

                    val responseBody =
                        objectMapper.readValue(result.response.contentAsByteArray, ExceptionResponseDTO::class.java)


                    responseBody.code shouldBe ExceptionCode.CAR_NOT_FOUND
                    responseBody.content shouldBe ExceptionCode.CAR_NOT_FOUND.message
                }
            }
        }
    }
}