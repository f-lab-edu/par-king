package api.adapter.`in`

import api.adapter.`in`.dto.MemberInfoDTO
import api.application.port.`in`.SaveMemberUseCase
import api.common.dto.SuccessResponseDTO
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class MemberCommandController(
    private val saveMemberUseCase: SaveMemberUseCase
) {
    @PostMapping("/sign-up")
    fun signUp(
        @RequestBody memberInfo: MemberInfoDTO?
    ): SuccessResponseDTO<String> {
        if(memberInfo?.password == null) return SuccessResponseDTO.success("가입 페이지")

        saveMemberUseCase.saveMember(memberInfo.toVO(), memberInfo.password)

        return SuccessResponseDTO.success("가입 완료 페이지")
    }
}