package com.parking.api.adapter.`in`

import com.parking.api.adapter.`in`.dto.CarInfoDTO
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
    ): SuccessResponseDTO<CarInfoDTO> {
        return SuccessResponseDTO.success(CarInfoDTO.from(findCarUseCase.findById(carId)))
    }
    @GetMapping("/find/member")
    fun findByMember(
        @RequestParam memberId: String
    ): SuccessResponseDTO<List<CarInfoDTO>> {
        return SuccessResponseDTO.success(findCarUseCase.findAllByMemberId(memberId).map { CarInfoDTO.from(it) })
    }
}