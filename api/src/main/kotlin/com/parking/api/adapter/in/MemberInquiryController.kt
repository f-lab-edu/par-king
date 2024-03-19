package com.parking.api.adapter.`in`

import com.parking.api.adapter.`in`.dto.MemberInfoResponseDTO
import com.parking.api.adapter.`in`.dto.SignInDTO
import com.parking.api.application.port.`in`.member.FindMemberUseCase
import com.parking.api.application.port.`in`.member.RefreshAccessToken
import com.parking.api.application.port.`in`.member.SignInMemberUseCase
import com.parking.api.common.dto.SuccessResponseDTO
import com.parking.api.common.jwt.Token
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/member")
class MemberInquiryController(
    private val findMemberUseCase: FindMemberUseCase,
    private val signInMemberUseCase: SignInMemberUseCase,
    private val refreshAccessToken: RefreshAccessToken
) {
    @GetMapping("/info")
    fun getMemberInfo(
        @RequestParam memberId: String
    ): SuccessResponseDTO<MemberInfoResponseDTO> {
        return SuccessResponseDTO.success(MemberInfoResponseDTO.from(findMemberUseCase.findMemberInfoByMemberId(memberId)))
    }
    @GetMapping("/sign-in")
    fun signIn(
        @RequestBody signInDTO: SignInDTO
    ): SuccessResponseDTO<Token> {
        return SuccessResponseDTO.success(signInMemberUseCase.signIn(signInDTO.memberId, signInDTO.password))
    }

    @GetMapping("/refresh")
    fun refresh(
        @RequestBody token: Token
    ): SuccessResponseDTO<Token> {
        return SuccessResponseDTO.success(refreshAccessToken.refreshAccessToken(token.refreshToken!!))
    }
}