package com.parking.api.adapter.`in`

import com.parking.api.adapter.`in`.dto.DibsOnParkingLotDTO
import com.parking.api.application.port.`in`.dibsOnParkingLot.DibsOnParkingLotUseCase
import com.parking.api.application.port.`in`.member.FindMemberUseCase
import com.parking.api.common.dto.SuccessResponseDTO
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dibs-on-parking-lot")
class DibsOnParkingLotCommandController(
    private val findMemberUseCase: FindMemberUseCase,
    private val dibsOnParkingLotUseCase: DibsOnParkingLotUseCase
) {
    @PostMapping("/request")
    fun requestDibsOnParkingLot(
        @RequestBody dibsOnParkingLotDTO: DibsOnParkingLotDTO
    ): SuccessResponseDTO<Boolean> {
        val memberId = findMemberUseCase.findIdByMemberId(dibsOnParkingLotDTO.memberId)
        dibsOnParkingLotUseCase.dibsOnParkingLot(memberId, dibsOnParkingLotDTO.parkingLotId, dibsOnParkingLotDTO.to())

        return SuccessResponseDTO.success(true)
    }
}