package com.parking.api.application.service

import com.parking.api.application.port.out.FindCarPort
import com.parking.api.application.port.out.FindMemberPort
import com.parking.api.application.port.out.FindParkingLotPort
import com.parking.api.application.vo.ResponseCarInfoVO
import com.parking.domain.entity.Car
import com.parking.domain.exception.CarException
import com.parking.domain.exception.MemberException
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

        val car = Car(1, "가1234", 1L)

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

                every { findCarPort.findById(carId) } returns car

                Then("자동차 정보 반환") {
                    val desireCarInfo = ResponseCarInfoVO.from(car, null)

                    val response = carInquiryService.findById(carId)

                    Assertions.assertEquals(desireCarInfo, response)
                }
            }
        }

        Given("memberId가 주어진 경우") {
            val memberId = "user1"

            When("현재 session 의 user 와 일치하지 않을 때") {
                val currentUser = "user2"

                Then("Exception 이 나와야 한다.") {
                    assertThrows<MemberException> {
                        carInquiryService.findAllByMemberId(currentUser, memberId)
                    }
                }
            }

            When("현재 session 의 user 와 일치하지만, memberId 가 존재하지 않을 때") {
                val currentUser = "user1"

                every { findMemberPort.findIdByMemberId(memberId) } returns null

                Then("Exception 발생") {
                    assertThrows<MemberException> {
                        carInquiryService.findAllByMemberId(currentUser, memberId)
                    }
                }
            }

            When("memberId 로 member 정보가 없을 때") {
                val currentUser = "user1"
                val id = 1L

                every { findMemberPort.findIdByMemberId(memberId) } returns id
                every { findCarPort.findAllByMemberId(id) } returns listOf(car)

                Then("응답 반환") {
                    val deSireResult = listOf(ResponseCarInfoVO.from(car, null))

                    val realResult = carInquiryService.findAllByMemberId(currentUser, memberId)

                    realResult.forEachIndexed { index, responseCarInfoVO ->
                        Assertions.assertEquals(responseCarInfoVO, deSireResult.get(index))
                    }
                }
            }
        }
    }
}