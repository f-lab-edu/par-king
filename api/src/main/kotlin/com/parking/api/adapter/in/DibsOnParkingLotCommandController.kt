package com.parking.api.adapter.`in`

import com.parking.api.adapter.`in`.dto.DibsOnParkingLotDTO
import com.parking.api.application.port.`in`.dibsOnParkingLot.DibsOnParkingLotUseCase
import com.parking.api.common.dto.SuccessResponseDTO
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dibs-on-parking-lot")
class DibsOnParkingLotCommandController(
    private val dibsOnParkingLotUseCase: DibsOnParkingLotUseCase
) {
    @PostMapping("/request")
    fun requestDibsOnParkingLot(
        @RequestBody dibsOnParkingLotDTO: DibsOnParkingLotDTO
    ): SuccessResponseDTO<Boolean> {
        dibsOnParkingLotUseCase.dibsOnParkingLot(dibsOnParkingLotDTO.to())

        return SuccessResponseDTO.success(true)
    }
}