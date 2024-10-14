package com.parking.api.adapter.common

import com.parking.api.common.jwt.UserDetailsImpl
import com.parking.domain.entity.Member
import com.parking.domain.entity.MemberInfo
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContextFactory


class WithMockUserSecurityContextFactory : WithSecurityContextFactory<WithMockUser> {
    override fun createSecurityContext(annotation: WithMockUser?): SecurityContext {
        val memberId = "test1011"
        val password = "123456"
        val member = Member(id = 1L, password = password,
            memberInfo = MemberInfo(memberId = memberId, name = "UserName", email = "User@User.com")
        )
        val userDetailsImpl = UserDetailsImpl(member, "123456")

        val auth = UsernamePasswordAuthenticationToken(userDetailsImpl, null, userDetailsImpl.authorities)

        val context = SecurityContextHolder.createEmptyContext()
//        context.authentication = UsernamePasswordAuthenticationToken(memberId, password, null)
        context.authentication = auth
        SecurityContextHolder.getContext().authentication = auth
        return context
    }
}