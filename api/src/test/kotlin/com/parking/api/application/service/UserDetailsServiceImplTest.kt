package com.parking.api.application.service

import com.parking.api.adapter.out.MemberInquiryAdapter
import com.parking.api.common.jwt.UserDetailsImpl
import com.parking.domain.entity.Member
import com.parking.domain.entity.MemberInfo
import com.parking.domain.exception.MemberException
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

class UserDetailsServiceImplTest : BehaviorSpec() {
    override fun isolationMode() = IsolationMode.SingleInstance

    init {
        val memberInquiryAdapter = mockk<MemberInquiryAdapter>()

        val userDetailServiceImpl = UserDetailsServiceImpl(memberInquiryAdapter)

        Given("username 이 있는 경우") {
            val username = "User1"

            When("username 이 db에 없을 때") {

                every { memberInquiryAdapter.findMemberInfoByMemberId(any()) } returns null

                Then("Exception 발생") {

                    assertThrows<MemberException> {
                        userDetailServiceImpl.loadUserByUsername(username)
                    }
                }
            }

            When("username 이 db에 있을 때") {
                val member = Member(id = 1L, password = "password",
                    memberInfo = MemberInfo(memberId = username, name = "UserName", email = "User@User.com")
                )


                every { memberInquiryAdapter.findMemberInfoByMemberId(any()) } returns member

                Then("userDetail 반환") {
                    val expectedResult = userDetailServiceImpl.loadUserByUsername(username)
                    val realResult = UserDetailsImpl(member, member.password!!)

                    Assertions.assertEquals(expectedResult.username, realResult.username)
                    Assertions.assertEquals(expectedResult.password, realResult.password)
                }
            }
        }
    }
}