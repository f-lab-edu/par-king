package com.parking.api.application.service

import com.parking.api.application.port.out.FindMemberPort
import com.parking.api.application.port.out.FindParkingLotPort
import com.parking.api.application.port.out.SaveParkingLotPort
import com.parking.api.application.vo.ParkingLotInfoVO
import com.parking.domain.exception.MemberException
import com.parking.domain.exception.ParkingLotException
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

class ParkingLotCommandServiceTest : BehaviorSpec() {
    override fun isolationMode() = IsolationMode.SingleInstance

    init {
        val findMemberPort = mockk<FindMemberPort>()
        val findParkingLotPort = mockk<FindParkingLotPort>()
        val saveParkingLotPort = mockk<SaveParkingLotPort>()

        val parkingLotCommandService = ParkingLotCommandService(findMemberPort, findParkingLotPort, saveParkingLotPort)

        Given("parkingLotId 가 주어졌을 때") {
            val parkingLotId = 1L

            When("대상 ParkingLot 이 없는 경우") {

                every { findParkingLotPort.findById(any()) } returns null

                Then("Exception 발생") {
                    assertThrows<ParkingLotException> {
                        parkingLotCommandService.delete(parkingLotId)
                    }
                }
            }

            When("대상 ParkingLot 이 삭제 되는 경우") {

                every { findParkingLotPort.findById(any()) } returns mockk()
                every { saveParkingLotPort.deleteUpdate(any(), any()) } returns mockk()

                Then("정상적으로 삭제 된다") {
                    parkingLotCommandService.delete(parkingLotId)
                }
            }
        }

        Given("ParkingLotInfoVO가 주어진 경우") {
            val noIdParkingLotInfoVO = ParkingLotInfoVO(
                "User1",
                name = "주차장1",
                totalSpace = 10L,
                cityName = "Seoul",
                guName = "Gang-nam"
            )

            val existIdParkingLotInfoVO = ParkingLotInfoVO(
                "User1",
                parkingLotId = 1L,
                name = "주차장1",
                totalSpace = 10L,
                cityName = "Seoul",
                guName = "Gang-nam"
            )

            When("memberId 가 존재하지 않을 때") {

                every { findMemberPort.findIdByMemberId(any()) } returns null

                Then("Exception 발생") {
                    assertThrows<MemberException> {
                        parkingLotCommandService.create(noIdParkingLotInfoVO)
                    }
                }
            }

            When("생성할 때") {

                val parkingLot = noIdParkingLotInfoVO.toParkingLot(1L)

                every { findMemberPort.findIdByMemberId(any()) } returns 1L
                every { saveParkingLotPort.save(any()) } returns parkingLot

                Then("생성 된다") {
                    val realResult = parkingLotCommandService.create(noIdParkingLotInfoVO)
                    val expectedResult = ParkingLotInfoVO.from(
                        noIdParkingLotInfoVO.memberId,
                        parkingLot
                    )

                    Assertions.assertEquals(expectedResult, realResult)
                }
            }

            When("수정할 때, ParkingLotId VO에 없는 경우") {
                Then("Exception 발생") {
                    assertThrows<ParkingLotException> {
                        parkingLotCommandService.modify(noIdParkingLotInfoVO)
                    }
                }
            }

            When("수정할 때, ParkingLotId 가 DB에 없는 경우") {

                every { findParkingLotPort.findById(any()) } returns null

                Then("Exception 발생") {
                    assertThrows<ParkingLotException> {
                        parkingLotCommandService.modify(existIdParkingLotInfoVO)
                    }
                }
            }
        }
    }
}