package com.parking.api.adapter.`in`

import com.parking.api.adapter.`in`.dto.ParkingLotInfoDTO
import com.parking.api.application.port.`in`.parkingLot.CreateParkingLotUseCase
import com.parking.api.application.port.`in`.parkingLot.DeleteParkingLotUseCase
import com.parking.api.common.dto.SuccessResponseDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/parking-lot")
class ParkingLotCommandController(
    private val createParkingLotUseCase: CreateParkingLotUseCase,
    private val deleteParkingLotUseCase: DeleteParkingLotUseCase
) {
    @PostMapping("/create")
    fun create(
        @RequestBody parkingLotInfo: ParkingLotInfoDTO
    ): SuccessResponseDTO<Boolean> {
        createParkingLotUseCase.create(parkingLotInfo.to())

        return SuccessResponseDTO.success(true)
    }

    @DeleteMapping("/delete/{parkingLotId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun delete(
        @PathVariable parkingLotId: Long
    ): SuccessResponseDTO<Boolean> {
        deleteParkingLotUseCase.delete(parkingLotId)

        return SuccessResponseDTO.success(true)
    }
}