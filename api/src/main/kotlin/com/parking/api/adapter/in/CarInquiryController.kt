package com.parking.api.adapter.`in`

import com.parking.api.adapter.`in`.dto.ResonseCarInfoDTO
import com.parking.api.application.port.`in`.car.FindCarUseCase
import com.parking.api.common.dto.SuccessResponseDTO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/car")
class CarInquiryController(
   private val findCarUseCase: FindCarUseCase
) {
    @GetMapping("/find/{carId}")
    fun find(
        @PathVariable carId: Long
    ): SuccessResponseDTO<ResonseCarInfoDTO> {
        return SuccessResponseDTO.success(ResonseCarInfoDTO.from(findCarUseCase.findById(carId)))
    }
    @GetMapping("/find/member")
    fun findByMember(
        @RequestParam memberId: String
    ): SuccessResponseDTO<List<ResonseCarInfoDTO>> {
        return SuccessResponseDTO.success(findCarUseCase.findAllByMemberId(memberId).map { ResonseCarInfoDTO.from(it) })
    }
}