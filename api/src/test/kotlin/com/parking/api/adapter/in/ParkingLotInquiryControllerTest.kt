package com.parking.api.adapter.`in`

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.parking.api.application.port.`in`.parkingLot.FindParkingLotUseCase
import com.parking.api.common.advice.ParkingAdvice
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
@ActiveProfiles(profiles = ["local","persistence-local"])
class ParkingLotInquiryControllerTest {

    val findParkingLotUseCase = mockk<FindParkingLotUseCase>()
    lateinit var mockMvc: MockMvc
    lateinit var mapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                ParkingLotInquiryController(findParkingLotUseCase)
            )
            .setControllerAdvice(
                ParkingAdvice::class.java
            )
            .setCustomArgumentResolvers(AuthenticationPrincipalArgumentResolver())
            .build()

        mapper = ObjectMapper().registerKotlinModule()
    }
}