package com.parking.api.application.service

import com.parking.api.adapter.out.MemberInquiryAdapter
import com.parking.api.common.jwt.UserDetailsImpl
import com.parking.domain.exception.MemberException
import com.parking.domain.exception.enum.ExceptionCode
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val memberInquiryAdapter: MemberInquiryAdapter
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val member = memberInquiryAdapter.findMemberInfoByMemberId(username)
            ?: throw MemberException(ExceptionCode.MEMBER_NOT_FOUND, "ID : $username 멤버가 존재하지 않습니다")

        return UserDetailsImpl(member, member.password!!)
    }
}