package com.parking.api.application.vo

import com.parking.domain.entity.ParkingLot

//주차장의 리스트로 보여질 데이터
data class ParkingLotListInfoVO(
    val parkingLotId: Long,
    val name: String,
    val cityName: String,
    val guName: String,
    val occupiedSpace: Long,
    val totalSpace: Long
) {
    companion object {
        fun from(parkingLot: ParkingLot): ParkingLotListInfoVO {
            val parkingLotInfo = parkingLot.parkingLotInfo
            val parkingLotLocation = parkingLot.parkingLotLocation

            return ParkingLotListInfoVO(
                parkingLotId = parkingLot.parkingLotId!!,
                name = parkingLotInfo.name,
                cityName = parkingLotLocation.cityName,
                guName = parkingLotLocation.guName,
                occupiedSpace = parkingLotInfo.occupiedSpace,
                totalSpace = parkingLotInfo.totalSpace
            )
        }
    }
}
