package com.parking.api.application.service

import com.parking.api.application.port.out.FindCarPort
import com.parking.api.application.port.out.FindMemberPort
import com.parking.api.application.port.out.FindParkingLotPort
import com.parking.api.application.vo.ResponseCarInfoVO
import com.parking.domain.entity.Car
import com.parking.domain.exception.CarException
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

class CarInquiryServiceTest : BehaviorSpec() {
    override fun isolationMode(): IsolationMode = IsolationMode.SingleInstance

    init {
        val findCarPort = mockk<FindCarPort>()
        val findParkingLotPort = mockk<FindParkingLotPort>()
        val findMemberPort = mockk<FindMemberPort>()

        val carInquiryService = CarInquiryService(findCarPort, findParkingLotPort, findMemberPort)

        Given("carId 가 있는 경우") {
            val carId = 1L

            When("정보 조회 시 정보가 없는 경우") {

                every { findCarPort.findById(carId) } returns null

                Then("Exception 발생") {
                    assertThrows<CarException> {
                        carInquiryService.findById(carId)
                    }
                }
            }

            When("정보 조회 시 정보가 있는 경우") {
                val car = Car(1, "가1234", 1L)

                every { findCarPort.findById(carId) } returns car

                Then("자동차 정보 반환") {
                    val desireCarInfo = ResponseCarInfoVO.from(car, null)

                    val response = carInquiryService.findById(carId)

                    Assertions.assertEquals(desireCarInfo, response)
                }
            }
        }
    }
}