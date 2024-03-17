package com.parking.api.adapter.`in`.dto

import com.parking.api.application.vo.ParkingLotInfoVO
import java.math.BigDecimal

data class ParkingLotInfoDTO(
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
    fun to() = ParkingLotInfoVO(
        memberId, name, fullAddress, totalSpace, occupiedSpace, cost, extraCost, cityName, guName
    )
}
