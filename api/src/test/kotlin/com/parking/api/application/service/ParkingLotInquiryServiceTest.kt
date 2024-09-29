package com.parking.api.application.service

import com.parking.api.application.port.out.FindMemberPort
import com.parking.api.application.port.out.FindParkingLotPort
import com.parking.api.application.vo.ParkingLotListInfoVO
import com.parking.domain.entity.ParkingLot
import com.parking.domain.entity.ParkingLotInfo
import com.parking.domain.entity.ParkingLotLocation
import com.parking.domain.exception.MemberException
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

class ParkingLotInquiryServiceTest : BehaviorSpec() {
    override fun isolationMode() = IsolationMode.SingleInstance

    init {
        val findMemberPort = mockk<FindMemberPort>()
        val findParkingLotPort = mockk<FindParkingLotPort>()

        val parkingLotInquiryService = ParkingLotInquiryService(findMemberPort, findParkingLotPort)

        Given("memberId 가 주어진 경우") {
            val memberId = "User1"
            val page = PageRequest.of(0, 5)

            When("Session User 와 요청 유저가 일치 하지 않을 때") {

                Then("Exception 발생") {
                    assertThrows<MemberException> {
                        parkingLotInquiryService.findAllByMemberId("User2", memberId, page)
                    }
                }
            }

            When("memberId 정보가 DB에 없을 때") {

                every { findMemberPort.findIdByMemberId(any()) } returns null

                Then("Exception 발생") {
                    assertThrows<MemberException> {
                        parkingLotInquiryService.findAllByMemberId("User1", memberId, page)
                    }
                }
            }

            When("memberId 로 조회한 parkingLot 정보들 반환") {

                val parkingLotLocation = ParkingLotLocation("seoul", "Yeok-sam")

                val emptyParkingLotInfo = ParkingLotInfo("full-parkingLot", null, 10L, 0L)
                val emptyParkingLot = ParkingLot(1L, 1L, emptyParkingLotInfo, parkingLotLocation)
                val pageResult = PageImpl(listOf(emptyParkingLot), page, 1L)

                every { findMemberPort.findIdByMemberId(any()) } returns 1L
                every { findParkingLotPort.findAllByMemberId(any(), any()) } returns pageResult

                Then("ParkingLot 정보들 반환") {

                    val realResult = parkingLotInquiryService.findAllByMemberId("User1", memberId, page)
                    val expectedResult = ParkingLotListInfoVO.from(emptyParkingLot)

                    realResult.forEach() {
                        Assertions.assertEquals(it, expectedResult)
                    }

                }
            }
        }
    }
}