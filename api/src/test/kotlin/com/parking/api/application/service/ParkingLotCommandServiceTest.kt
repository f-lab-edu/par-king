package com.parking.api.application.service

import com.parking.api.application.port.out.FindMemberPort
import com.parking.api.application.port.out.FindParkingLotPort
import com.parking.api.application.port.out.SaveParkingLotPort
import com.parking.domain.exception.ParkingLotException
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
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
    }
}