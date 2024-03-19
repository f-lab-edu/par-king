package com.parking.api.adapter.`in`

import com.parking.api.adapter.`in`.dto.ParkingLotListInfoDTO
import com.parking.api.application.port.`in`.parkingLot.FindParkingLotUseCase
import com.parking.api.common.dto.SuccessResponseDTO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/parking-lot")
class ParkingLotInquiryController(
   private val findParkingLotUseCase: FindParkingLotUseCase
) {
    //내가 등록한 주차장 보기
    @GetMapping("/find/member")
    fun findMyParkingLots(
        @RequestParam memberId: String
    ): SuccessResponseDTO<List<ParkingLotListInfoDTO>> {
        val parkingLotList = findParkingLotUseCase.findAllByMemberId(memberId).map { ParkingLotListInfoDTO.from(it) }

        return SuccessResponseDTO.success(parkingLotList)
    }
}