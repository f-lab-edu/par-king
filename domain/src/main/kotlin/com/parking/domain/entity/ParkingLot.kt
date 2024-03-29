package com.parking.domain.entity

import java.math.BigDecimal

data class ParkingLot(
    var parkingLotId: Long? = null,
    val memberId: Long,
    val parkingLotInfo: ParkingLotInfo,
    val parkingLotLocation: ParkingLotLocation
)

data class ParkingLotInfo(
    val name: String,
    val fullAddress: String?,
    val totalSpace: Long,
    val occupiedSpace: Long = 0L,
    //1시간 기준 기본 요금
    val cost: BigDecimal = BigDecimal.ZERO,
    //1시간이 지난 후 10분마다 추가되는 요금
    val extraCost: BigDecimal = BigDecimal.ZERO
)

data class ParkingLotLocation(
    val cityName: String,
    val guName: String
)
