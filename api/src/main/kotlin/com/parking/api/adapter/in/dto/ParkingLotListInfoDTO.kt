package com.parking.api.adapter.`in`.dto

import com.parking.api.application.vo.ParkingLotListInfoVO

//주차장의 리스트로 보여질 데이터
data class ParkingLotListInfoDTO(
    val parkingLotId: Long,
    val name: String,
    val cityName: String,
    val guName: String,
    val occupiedSpace: Long,
    val totalSpace: Long
) {
    companion object {
        fun from(parkingLot: ParkingLotListInfoVO) = ParkingLotListInfoDTO (
                parkingLotId = parkingLot.parkingLotId,
                name = parkingLot.name,
                cityName = parkingLot.cityName,
                guName = parkingLot.guName,
                occupiedSpace = parkingLot.occupiedSpace,
                totalSpace = parkingLot.totalSpace
        )
    }
}