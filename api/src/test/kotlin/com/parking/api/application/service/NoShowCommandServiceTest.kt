package com.parking.api.application.service

import com.parking.api.application.port.out.*
import com.parking.domain.entity.*
import com.parking.domain.exception.MemberException
import com.parking.domain.exception.NoShowException
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class NoShowCommandServiceTest : BehaviorSpec() {
    override fun isolationMode() = IsolationMode.SingleInstance

    init {
        val findMemberPort = mockk<FindMemberPort>()
        val saveMemberPort = mockk<SaveMemberPort>()
        val findNoShowPort = mockk<FindNoShowPort>()
        val saveNoShowPort = mockk<SaveNoShowPort>()
        val saveDibsOnParkingLotPort = mockk<SaveDibsOnParkingLotPort>()

        val noShowCommandService = NoShowCommandService(
            findMemberPort,
            saveMemberPort,
            findNoShowPort,
            saveNoShowPort,
            saveDibsOnParkingLotPort
        )

        Given("memberId, 찜 정보가 주어진 경우") {
            val memberId = 1L
            val dibsOnParkingLot = DibsOnParkingLot(
                1L,
                1L,
                1L,
                1L,
                DibsOnParkingLotStatus.DIBS_ON_PARKING_LOT
            )

            When("memberId 가 DB에 존재하지 않을 때") {

                every { findMemberPort.findById(any()) } returns null

                Then("Exception 발생") {
                    assertThrows<MemberException> {
                        noShowCommandService.save(memberId, dibsOnParkingLot)
                    }
                }
            }

            When("최근 Noshow 가 있는데 limit Time 이내인 경우(잘못들어온 요청)") {
                val member = Member(id = 1L,
                    memberInfo = MemberInfo(memberId = "User1", name = "UserName", email = "User@User.com")
                )
                val noShow = NoShow(null, memberId, 1L, 1L, LocalDateTime.now())

                every { findMemberPort.findById(any()) } returns member
                every { findNoShowPort.findRecentlyNoShow(any(), any()) } returns noShow

                Then("Exception 발생") {
                    assertThrows<NoShowException> {
                        noShowCommandService.save(memberId, dibsOnParkingLot)
                    }
                }
            }
        }
    }
}