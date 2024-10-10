package com.parking.api.adapter.`in`

import com.fasterxml.jackson.databind.ObjectMapper
import com.parking.api.adapter.`in`.dto.RegisterCarDTO
import com.parking.api.adapter.`in`.dto.ResponseCarInfoDTO
import com.parking.api.application.port.`in`.car.SaveCarUseCase
import com.parking.api.application.vo.ResponseCarInfoVO
import com.parking.api.common.dto.SuccessResponseDTO
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders


@SpringBootTest
@ActiveProfiles(profiles = ["local","persistence-local"])
class CarCommandControllerTest @Autowired constructor(
    private val objectMapper: ObjectMapper
) : DescribeSpec(){

    val saveCarUseCase = mockk<SaveCarUseCase>()
    private lateinit var mockMvc: MockMvc

    init { extensions(SpringExtension)
        this.beforeTest {
            mockMvc = MockMvcBuilders.standaloneSetup(
                CarCommandController(saveCarUseCase)
            ).build()
        }

        this.describe("CarCommandController Test") {
            context("자동차 정보가 정상적으로 들어오면") {
                it("200 응답과 저장한 차 정보가 반환돼야 한다.") {
                    val registerCar = RegisterCarDTO(
                        memberId = "user1",
                        carNumber = "가1234"
                    )
                    val responseFromUseCase = ResponseCarInfoVO(
                        1L,
                        carNumber = "가1234"
                    )
                    every { saveCarUseCase.save(any()) } returns responseFromUseCase

                    val result = mockMvc.perform(
                        post("/car/save")

                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registerCar))
                    )
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()


                    val responseBody =
                        objectMapper.readValue(result.response.contentAsByteArray, SuccessResponseDTO::class.java)

                    val responseContent: ResponseCarInfoDTO =
                        objectMapper.convertValue(responseBody.content, ResponseCarInfoDTO::class.java)

                    responseContent.carNumber shouldBe registerCar.carNumber
                    result.response.status shouldBe HttpStatus.OK.value()
                }
            }

        }
    }

}