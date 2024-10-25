package com.parking.api.adapter.`in`

import com.fasterxml.jackson.databind.ObjectMapper
import com.parking.api.application.port.`in`.parkingLot.CreateParkingLotUseCase
import com.parking.api.application.port.`in`.parkingLot.DeleteParkingLotUseCase
import com.parking.api.application.port.`in`.parkingLot.ModifyParkingLotUseCase
import com.parking.api.common.advice.ParkingAdvice
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.mockk
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
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


    }
}