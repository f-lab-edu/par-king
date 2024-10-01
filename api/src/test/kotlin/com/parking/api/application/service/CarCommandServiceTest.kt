package com.parking.api.application.service

import com.parking.api.application.port.out.FindMemberPort
import com.parking.api.application.port.out.SaveCarPort
import com.parking.api.application.vo.RegisterCarVO
import com.parking.api.application.vo.ResponseCarInfoVO
import com.parking.domain.entity.Car
import com.parking.domain.exception.MemberException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions

class CarCommandServiceTest : BehaviorSpec() {
    override fun isolationMode(): IsolationMode = IsolationMode.SingleInstance

    init {

        val findMemberPort = mockk<FindMemberPort>()
        val saveCarPort = mockk<SaveCarPort>()

        val carCommandService = CarCommandService(findMemberPort, saveCarPort)

        Given("자동차 정보가 있을 경우") {

            val carInfo = RegisterCarVO("1", "가1234")

            When("조회시 값이 없을 때") {
                every { findMemberPort.findIdByMemberId(any()) } returns null

                Then("Exception 발생") {
                    shouldThrow<MemberException> {
                        carCommandService.save(carInfo)
                    }
                }
            }

            When("조회 정보가 존재 할 때") {
                val car = Car(1, carInfo.carNumber, 1L)

                every { findMemberPort.findIdByMemberId(any()) } returns 1
                every { saveCarPort.save(any()) } returns car

                Then("저장후 정보 반환") {
                    val response = carCommandService.save(carInfo)
                    val desiredValue = ResponseCarInfoVO.from(car, null)

                    Assertions.assertEquals(response, desiredValue)
                }
            }
        }
    }
}