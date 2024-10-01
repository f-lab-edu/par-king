package com.parking.api.application.service

import com.parking.api.application.port.out.*
import com.parking.api.application.vo.DibsOnParkingLotVO
import com.parking.domain.entity.*
import com.parking.domain.exception.CarException
import com.parking.domain.exception.DibsOnParkingLotException
import com.parking.domain.exception.ParkingLotException
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.assertThrows

class DibsOnParkingLotCommandServiceTest : BehaviorSpec() {
    override fun isolationMode() =  IsolationMode.SingleInstance

    init {
        val findCarPort = mockk<FindCarPort>()
        val saveCarPort = mockk<SaveCarPort>()
        val findParkingLotPort = mockk<FindParkingLotPort>()
        val saveParkingLotPort = mockk<SaveParkingLotPort>()
        val saveDibsOnParkingLotPort = mockk<SaveDibsOnParkingLotPort>()

        val dibsOnParkingLotCommandService = DibsOnParkingLotCommandService(
            findCarPort,
            saveCarPort,
            findParkingLotPort,
            saveParkingLotPort,
            saveDibsOnParkingLotPort
        )

        Given("memberId, parkingLotId, 찜하기 정보가 주어졌을 때") {
            val memberId = 1L
            val parkingLotId = 1L
            val carId = 1L
            val dibsOnParkingLotVO = DibsOnParkingLotVO("User1", parkingLotId, carId)
            val parkingLotLocation = ParkingLotLocation("seoul", "Yeok-sam")

            val fullParkingLotInfo = ParkingLotInfo("full-parkingLot", null, 5L, 5L)
            val emptyParkingLotInfo = ParkingLotInfo("full-parkingLot", null, 10L, 0L)
            val fullParkingLot = ParkingLot(parkingLotId, memberId, fullParkingLotInfo, parkingLotLocation)
            val emptyParkingLot = ParkingLot(parkingLotId, memberId, emptyParkingLotInfo, parkingLotLocation)

            val unavailableCar = Car(
                null,
                "가1234",
                memberId,
                parkingLotId,
                1L,
                DibsOnParkingLotStatus.DIBS_ON_PARKING_LOT,
                null
            )

            val availableCar = Car(
                null,
                "가1234",
                memberId,
                parkingLotId,
                1L,
                DibsOnParkingLotStatus.NORMAL,
                null
            )

            When("ParkingLot 정보를 찾을 수 없는 경우") {

                every { findParkingLotPort.findById(any()) } returns null

                Then("Exception 발생") {
                    assertThrows<ParkingLotException> {
                        dibsOnParkingLotCommandService.dibsOnParkingLot(memberId, parkingLotId, dibsOnParkingLotVO)
                    }
                }
            }

            When("주차장이 꽉 찬 경우") {

                every { findParkingLotPort.findById(any()) } returns fullParkingLot

                Then("Exception 발생") {
                    assertThrows<ParkingLotException> {
                        dibsOnParkingLotCommandService.dibsOnParkingLot(memberId, parkingLotId, dibsOnParkingLotVO)
                    }
                }
            }

            When("carId 의 정보가 db에 없을 때") {

                every { findParkingLotPort.findById(any()) } returns emptyParkingLot
                every { findCarPort.findById(carId) } returns null

                Then("Exception 발생") {
                    assertThrows<CarException> {
                        dibsOnParkingLotCommandService.dibsOnParkingLot(memberId, parkingLotId, dibsOnParkingLotVO)
                    }
                }
            }

            When("자동차가 이미 찜 상태인 경우") {

                every { findParkingLotPort.findById(any()) } returns emptyParkingLot
                every { findCarPort.findById(carId) } returns unavailableCar

                then("Exception 발생") {
                    assertThrows<DibsOnParkingLotException> {
                        dibsOnParkingLotCommandService.dibsOnParkingLot(memberId, parkingLotId, dibsOnParkingLotVO)
                    }
                }
            }

            When("자동차의 찜 정보 Normal 일 경우") {

                val dibsOnParkingLot = DibsOnParkingLot(
                    1L, memberId, parkingLotId, carId
                )

                every { findParkingLotPort.findById(any()) } returns emptyParkingLot
                every { findCarPort.findById(carId) } returns availableCar
                every { saveDibsOnParkingLotPort.save(any()) } returns dibsOnParkingLot
                every { saveParkingLotPort.save(any()) } returns mockk{}
                every { saveCarPort.save(any()) } returns mockk{}

                Then("찜이 정상 수행 돼야 한다.") {
                    dibsOnParkingLotCommandService.dibsOnParkingLot(memberId, parkingLotId, dibsOnParkingLotVO)
                }
            }
        }
    }
}