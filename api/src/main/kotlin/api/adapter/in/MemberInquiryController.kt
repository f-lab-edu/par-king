package api.adapter.`in`

import api.adapter.`in`.dto.MemberInfoResponseDTO
import api.adapter.`in`.dto.SignInDTO
import api.application.port.`in`.FindMemberUseCase
import api.application.port.`in`.CreateTokenUsingRefreshTokenUseCase
import api.application.port.`in`.SignInMemberUseCase
import api.common.dto.SuccessResponseDTO
import api.common.jwt.Token
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/member")
class MemberInquiryController(
    private val findMemberUseCase: FindMemberUseCase,
    private val signInMemberUseCase: SignInMemberUseCase,
    private val createTokenUsingRefreshTokenUseCase: CreateTokenUsingRefreshTokenUseCase
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
        return SuccessResponseDTO.success(createTokenUsingRefreshTokenUseCase.createAccessToken(token.refreshToken!!))
    }
}