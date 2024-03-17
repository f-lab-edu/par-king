package com.parking.api.adapter.`in`

import com.parking.api.adapter.`in`.dto.ParkingLotInfoDTO
import com.parking.api.application.port.`in`.CreateParkingLotUseCase
import com.parking.api.common.dto.SuccessResponseDTO
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/parking-lot")
class ParkingLotCommandController(
   private val createParkingLotUseCase: CreateParkingLotUseCase
) {
    @PostMapping("/create")
    fun create(
        @RequestBody parkingLotInfo: ParkingLotInfoDTO
    ): SuccessResponseDTO<Boolean> {
        createParkingLotUseCase.create(parkingLotInfo.to())

        return SuccessResponseDTO.success(true)
    }
}