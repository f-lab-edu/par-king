package api.application.service

import api.adapter.out.MemberInquiryAdapter
import api.common.jwt.UserDetailsImpl
import domain.exception.MemberException
import domain.exception.enum.ExceptionCode
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