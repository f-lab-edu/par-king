package com.parking.api.adapter.`in`

import com.parking.api.adapter.`in`.dto.ParkingLotListInfoDTO
import com.parking.api.adapter.`in`.dto.ParkingLotLocationDTO
import com.parking.api.application.port.`in`.parkingLot.FindParkingLotUseCase
import com.parking.api.common.dto.PageContentDTO
import com.parking.api.common.dto.SuccessResponseDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
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
        @AuthenticationPrincipal currentUser: UserDetails,
        @RequestParam memberId: String,
        @RequestParam page: Int, @RequestParam size: Int
    ): SuccessResponseDTO<Page<ParkingLotListInfoDTO>> {
        val parkingLotList =
            findParkingLotUseCase.findAllByMemberId(currentUser.username, memberId, PageRequest.of(page, size))
                .map { ParkingLotListInfoDTO.from(it) }

        return SuccessResponseDTO.success(parkingLotList)
    }

    @GetMapping("/find/location")
    fun findParkingLots(
        @RequestBody location: ParkingLotLocationDTO,
        @RequestParam page: Int, @RequestParam size: Int
    ): PageContentDTO<List<ParkingLotListInfoDTO>> {
        val parkingLotList =
            findParkingLotUseCase.findAllByLocation(location.to(), PageRequest.of(page, size))
                .map { ParkingLotListInfoDTO.from(it) }

        return PageContentDTO.success(parkingLotList.content, parkingLotList.number, parkingLotList.totalPages)
    }
}