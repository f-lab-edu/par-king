package com.parking.api.adapter.`in`

import com.fasterxml.jackson.databind.ObjectMapper
import com.parking.api.adapter.`in`.dto.ParkingLotInfoDTO
import com.parking.api.application.port.`in`.parkingLot.CreateParkingLotUseCase
import com.parking.api.application.port.`in`.parkingLot.DeleteParkingLotUseCase
import com.parking.api.application.port.`in`.parkingLot.ModifyParkingLotUseCase
import com.parking.api.application.vo.ParkingLotInfoVO
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
class ParkingLotCommandControllerTest(
    private val mapper: ObjectMapper
) : DescribeSpec() {
    init {
        lateinit var mockMvc: MockMvc
        val createParkingLotUseCase = mockk<CreateParkingLotUseCase>()
        val deleteParkingLotUseCase = mockk<DeleteParkingLotUseCase>()
        val modifyParkingLotUseCase = mockk<ModifyParkingLotUseCase>()

        this.beforeTest {
            mockMvc = MockMvcBuilders.standaloneSetup(
                ParkingLotCommandController(createParkingLotUseCase, deleteParkingLotUseCase, modifyParkingLotUseCase)
            )
                .setControllerAdvice(
                    ParkingAdvice::class.java
                )
                .build()
        }

        this.describe("ParkingLotCommandController Test") {
            context("생성하는 경우") {
                val parkingLotInfoVO = ParkingLotInfoVO(
                    "User1",
                    parkingLotId = 1L,
                    name = "주차장1",
                    totalSpace = 10L,
                    cityName = "Seoul",
                    guName = "Gang-nam"
                )
                val parkingLotInfoDTO = ParkingLotInfoDTO.from(parkingLotInfoVO)

                every { createParkingLotUseCase.create(any()) } returns parkingLotInfoVO

                it("생성한 경우가 나와야 한다.") {
                    val result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/parking-lot/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(parkingLotInfoDTO))
                    )
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()


                    val responseBody =
                        mapper.readValue(result.response.contentAsByteArray, SuccessResponseDTO::class.java)


                    val realResult =
                        mapper.convertValue(responseBody.content, ParkingLotInfoDTO::class.java)

                    realResult shouldBe parkingLotInfoDTO
                }
            }
        }
    }
}