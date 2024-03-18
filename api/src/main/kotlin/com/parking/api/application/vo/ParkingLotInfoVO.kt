package com.parking.api.application.vo

import com.parking.domain.entity.ParkingLot
import com.parking.domain.entity.ParkingLotInfo
import com.parking.domain.entity.ParkingLotLocation
import java.math.BigDecimal
import java.time.LocalDateTime

data class ParkingLotInfoVO(
    val memberId: String,
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
    fun toParkingLot(memberId: Long, deletedAt: LocalDateTime) =
        ParkingLot(
            memberId = memberId,
            parkingLotInfo = toParkingLotInfo(),
            parkingLotLocation = toParkingLotLocation(),
            deletedAt = deletedAt
        )

    private fun toParkingLotInfo() = ParkingLotInfo(
        name, fullAddress, totalSpace, occupiedSpace, cost, extraCost
    )

    private fun toParkingLotLocation() = ParkingLotLocation(cityName, guName)
}
