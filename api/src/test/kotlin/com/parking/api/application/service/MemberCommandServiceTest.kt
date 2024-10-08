package com.parking.api.application.service

import com.parking.api.application.port.out.FindMemberPort
import com.parking.api.application.port.out.SaveMemberPort
import com.parking.api.application.vo.MemberInfoVO
import com.parking.domain.entity.Member
import com.parking.domain.entity.MemberInfo
import com.parking.domain.exception.MemberException
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.password.PasswordEncoder

class MemberCommandServiceTest : BehaviorSpec() {
    override fun isolationMode() =  IsolationMode.SingleInstance

    init {
        val findMemberPort =  mockk<FindMemberPort>()
        val saveMemberPort =  mockk<SaveMemberPort>()
        val passwordEncoder =  mockk<PasswordEncoder>()

        val memberCommandService = MemberCommandService(findMemberPort, saveMemberPort, passwordEncoder)

        Given("탈퇴시 memberId 만 주어진 경우") {
            val memberId = "user1"

            When("DB 에 멤버 정보가 없는 경우") {

                every { findMemberPort.findMemberInfoByMemberId(any()) } returns null

                Then("Exception 발생") {
                    assertThrows<MemberException> {
                        memberCommandService.revoke(memberId)
                    }
                }
            }

            When("탈퇴된 회원 정보 전환 실패한 경우") {
                val member = Member(id = 1L, password = "password",
                    memberInfo = MemberInfo(memberId = "User1", name = "UserName", email = "User@User.com")
                )
                val spyMember = spyk(member)

                every { findMemberPort.findMemberInfoByMemberId(any()) } returns spyMember
                every { spyMember.isRevoked() } returns false

                Then("탈퇴 성공 후 멤버 정보 반환") {
                    assertThrows<MemberException> {
                        memberCommandService.revoke(memberId)
                    }
                }
            }

            When("탈퇴된 회원 정보 저장 실패한 경우") {
                val member = Member(id = 1L, password = "password",
                    memberInfo = MemberInfo(memberId = "User1", name = "UserName", email = "User@User.com")
                )

                every { findMemberPort.findMemberInfoByMemberId(any()) } returns member

                member.revoke()

                every { saveMemberPort.saveMember(any()) } returns null

                Then("탈퇴 성공 후 멤버 정보 반환") {
                    assertThrows<MemberException> {
                        memberCommandService.revoke(memberId)
                    }
                }
            }

            When("정상적으로 탈퇴하는 경우") {
                val member = Member(id = 1L, password = "password",
                    memberInfo = MemberInfo(memberId = "User1", name = "UserName", email = "User@User.com")
                )

                every { findMemberPort.findMemberInfoByMemberId(any()) } returns member

                member.revoke()

                every { saveMemberPort.saveMember(any()) } returns member

                Then("탈퇴 성공 후 멤버 정보 반환") {
                    val realResult = memberCommandService.revoke(memberId)
                    val expectedResult = MemberInfoVO.from(member)

                    Assertions.assertEquals(expectedResult, realResult)
                }
            }
        }
    }
}
