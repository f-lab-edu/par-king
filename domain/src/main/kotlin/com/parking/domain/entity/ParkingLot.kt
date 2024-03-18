package com.parking.domain.entity

import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import kotlin.random.Random

data class ParkingLot(
    var parkingLotId: Long? = null,
    val memberId: Long,
    val parkingLotInfo: ParkingLotInfo,
    val parkingLotLocation: ParkingLotLocation,
    //ParkingLot 이 지워지는 시점을 기록하는 변수
    var deletedAt: LocalDateTime
) {
    fun delete() {
        deletedAt = LocalDateTime.now()
    }

    companion object {
        //5년 범위 안에 랜덤 값
        private val RANDOM_RANGE = 60L * 60 * 24 * 365 * 5
        fun makeDeletedAt(): LocalDateTime {
            val initTime = LocalDateTime.ofEpochSecond(0L, 0, ZoneOffset.UTC)
            val random = Random.nextLong(RANDOM_RANGE)

            return initTime.minus(random, ChronoUnit.SECONDS)
        }
    }
}

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
