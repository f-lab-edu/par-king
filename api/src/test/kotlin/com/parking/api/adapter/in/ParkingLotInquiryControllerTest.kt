package com.parking.api.adapter.`in`

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.parking.api.adapter.common.StringUtil.toObjectList
import com.parking.api.adapter.`in`.dto.ParkingLotListInfoDTO
import com.parking.api.adapter.`in`.dto.ParkingLotLocationDTO
import com.parking.api.application.port.`in`.parkingLot.FindParkingLotUseCase
import com.parking.api.application.vo.ParkingLotListInfoVO
import com.parking.api.common.advice.ParkingAdvice
import com.parking.api.common.dto.PageContentDTO
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
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


    @Test
    @DisplayName("지역을 대상으로 주차장 정보 반환 테스트")
    fun findByLocation() {
        val location = ParkingLotLocationDTO("seoul", "gang-nam")
        val parkingLotInfo = ParkingLotListInfoVO(
            1L,
            "parkinglot1",
            location.cityName!!,
            location.guName!!,
            10L,
            40L
        )
        val page = PageRequest.of(0, 5)
        val pageResult = PageImpl(listOf(parkingLotInfo), page, 1L)

        every { findParkingLotUseCase.findAllByLocation(any(), any()) } returns pageResult

        val result = mockMvc.perform(
                MockMvcRequestBuilders.get("/parking-lot/find/location?page=0&size=5")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(location))
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val responseBody =
            mapper.readValue(result.response.contentAsString, PageContentDTO::class.java)

        val convertPageResult = mapper.writeValueAsString(responseBody.content).toObjectList<ParkingLotListInfoDTO>()

        val expectedResult = listOf(ParkingLotListInfoDTO.from(parkingLotInfo))

        expectedResult.forEachIndexed{idx, it ->
            it.cityName shouldBe convertPageResult[idx].cityName
            it.guName shouldBe convertPageResult[idx].guName
            it.name shouldBe convertPageResult[idx].name
            it.occupiedSpace shouldBe convertPageResult[idx].occupiedSpace
            it.totalSpace shouldBe convertPageResult[idx].totalSpace
        }
    }
}