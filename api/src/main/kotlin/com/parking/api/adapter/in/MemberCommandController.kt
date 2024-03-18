package com.parking.api.adapter.`in`

import com.parking.api.adapter.`in`.dto.MemberInfoDTO
import com.parking.api.adapter.`in`.dto.MemberInfoResponseDTO
import com.parking.api.adapter.`in`.dto.SignUpDTO
import com.parking.api.application.port.`in`.ModifyMemberInfoUseCase
import com.parking.api.application.port.`in`.RevokeMemberUseCase
import com.parking.api.application.port.`in`.SaveMemberUseCase
import com.parking.api.common.dto.SuccessResponseDTO
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class MemberCommandController(
    private val saveMemberUseCase: SaveMemberUseCase,
    private val modifyMemberInfoUseCase: ModifyMemberInfoUseCase,
    private val revokeMemberUseCase: RevokeMemberUseCase
) {
    @PostMapping("/sign-up")
    fun signUp(
        @RequestBody signUpDTO: SignUpDTO
    ): SuccessResponseDTO<MemberInfoResponseDTO> {

        return SuccessResponseDTO.success(
            MemberInfoResponseDTO.from(
                saveMemberUseCase.saveMember(
                    signUpDTO.toMemberInfoVO(),
                    signUpDTO.password
                )
            )
        )
    }

    @PostMapping("/modify")
    fun modify(
        @RequestBody memberInfoDTO: MemberInfoDTO
    ): SuccessResponseDTO<MemberInfoResponseDTO> {
        return SuccessResponseDTO.success(MemberInfoResponseDTO.from(modifyMemberInfoUseCase.modify(memberInfoDTO.to())))
    }

    @PostMapping("/revoke")
    fun revoke(
        @RequestParam memberId: String
    ): SuccessResponseDTO<Boolean> {
        revokeMemberUseCase.revoke(memberId)

        return SuccessResponseDTO.success(true)
    }
}