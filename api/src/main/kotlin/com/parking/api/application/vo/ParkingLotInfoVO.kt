package com.parking.api.application.vo

import com.parking.domain.entity.ParkingLot
import com.parking.domain.entity.ParkingLotInfo
import com.parking.domain.entity.ParkingLotLocation
import java.math.BigDecimal

data class ParkingLotInfoVO(
    val memberId: String,
    val parkingLotId: Long? = null,
    val name: String,
    val fullAddress: String? = null,
    val totalSpace: Long,
    val occupiedSpace: Long = 0L,
    //1시간 기준 기본 요금
    val cost: BigDecimal = BigDecimal.ZERO,
    //1시간이 지난 후 10분마다 추가되는 요금
    val extraCost: BigDecimal = BigDecimal.ZERO,
    val cityName: String,
    val guName: String
) {
    companion object {
        fun from(memberId: String, parkingLot: ParkingLot): ParkingLotInfoVO {
            val parkingLotInfo = parkingLot.parkingLotInfo
            val parkingLotLocation = parkingLot.parkingLotLocation

            return ParkingLotInfoVO(
                memberId= memberId,
                parkingLotId = parkingLot.parkingLotId,
                name = parkingLotInfo.name,
                fullAddress = parkingLotInfo.fullAddress,
                totalSpace = parkingLotInfo.totalSpace,
                occupiedSpace = parkingLotInfo.occupiedSpace,
                cost = parkingLotInfo.cost,
                extraCost = parkingLotInfo.extraCost,
                cityName = parkingLotLocation.cityName,
                guName = parkingLotLocation.guName
            )
        }
    }
    fun toParkingLot(memberId: Long) =
        ParkingLot(
            memberId = memberId,
            parkingLotInfo = toParkingLotInfo(),
            parkingLotLocation = toParkingLotLocation()
        )

    private fun toParkingLotInfo() = ParkingLotInfo(
        name, fullAddress, totalSpace, occupiedSpace, cost, extraCost
    )

    private fun toParkingLotLocation() = ParkingLotLocation(cityName, guName)
}
