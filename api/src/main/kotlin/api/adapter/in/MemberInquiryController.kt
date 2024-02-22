package api.adapter.`in`

import api.adapter.`in`.dto.MemberInfoResponseDTO
import api.application.port.`in`.FindMemberUseCase
import api.common.dto.SuccessResponseDTO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class MemberInquiryController(
    private val findMemberUseCase: FindMemberUseCase
) {
    @GetMapping("/member-info")
    fun getMemberInfo(
        @RequestParam memberId: Long
    ) : SuccessResponseDTO<MemberInfoResponseDTO> {
        return SuccessResponseDTO.success(MemberInfoResponseDTO.from(findMemberUseCase.findMemberInfoByMemberId(memberId)))
    }
}