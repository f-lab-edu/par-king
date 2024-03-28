package com.parking.api.adapter.`in`

import com.parking.api.adapter.`in`.dto.RegisterCarDTO
import com.parking.api.adapter.`in`.dto.ResponseCarInfoDTO
import com.parking.api.application.port.`in`.car.SaveCarUseCase
import com.parking.api.common.dto.SuccessResponseDTO
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/car")
class CarCommandController(
    private val saveCarUseCase: SaveCarUseCase
) {

    @PostMapping("/save")
    fun save(
        @RequestBody registerCar: RegisterCarDTO
    ): SuccessResponseDTO<ResponseCarInfoDTO> {
        return SuccessResponseDTO.success(ResponseCarInfoDTO.from(saveCarUseCase.save(registerCar.toVO())))
    }
}