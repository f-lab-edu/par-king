package com.parking.api.adapter.`in`

import com.parking.api.adapter.`in`.dto.ResponseCarInfoDTO
import com.parking.api.application.port.`in`.car.FindCarUseCase
import com.parking.api.common.dto.SuccessResponseDTO
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/car")
class CarInquiryController(
   private val findCarUseCase: FindCarUseCase
) {
    @GetMapping("/find/{carId}")
    fun find(
        @PathVariable carId: Long
    ): SuccessResponseDTO<ResponseCarInfoDTO> {
        return SuccessResponseDTO.success(ResponseCarInfoDTO.from(findCarUseCase.findById(carId)))
    }
    @GetMapping("/find/member")
    fun findByMember(
        @AuthenticationPrincipal currentUser: UserDetails,
        @RequestParam memberId: String
    ): SuccessResponseDTO<List<ResponseCarInfoDTO>> {
        return SuccessResponseDTO.success(findCarUseCase.findAllByMemberId(currentUser.username, memberId).map { ResponseCarInfoDTO.from(it) })
    }
}