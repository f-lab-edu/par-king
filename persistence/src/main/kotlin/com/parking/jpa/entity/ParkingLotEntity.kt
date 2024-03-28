package com.parking.jpa.entity

import com.parking.domain.entity.ParkingLot
import com.parking.domain.entity.ParkingLotInfo
import com.parking.domain.entity.ParkingLotLocation
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import kotlin.random.Random


@Entity
@Table(name = "parking_lot")
data class ParkingLotEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "member_id")
    val memberId: Long,

    val name: String,

    @Column(name = "gu_name")
    val guName: String,

    @Column(name = "city_name")
    val cityName: String,

    @Column(name = "full_address")
    val fullAddress: String? = null,

    @Column(name = "total_space")
    val totalSpace: Long,

    @Column(name = "occupied_space")
    val occupiedSpace: Long,

    val cost: BigDecimal = BigDecimal.ZERO,

    @Column(name = "extra_cost")
    val extraCost: BigDecimal = BigDecimal.ZERO,

    //ParkingLot 이 지워지는 시점을 기록하는 변수
    @Column(name = "deleted_at")
    val deletedAt: LocalDateTime

) : BaseEntity() {
    fun to() = ParkingLot(
        parkingLotId = id,
        memberId = memberId,
        parkingLotInfo = ParkingLotInfo(name, fullAddress, totalSpace, occupiedSpace, cost, extraCost),
        parkingLotLocation = ParkingLotLocation(cityName, guName)
    )

    companion object {
        val STANDARD_DELETED_AT_TIME = LocalDateTime.ofEpochSecond(0L, 0, ZoneOffset.UTC)

        fun from(parkingLot: ParkingLot, deletedAt: LocalDateTime?): ParkingLotEntity {
            val RANDOM_RANGE = 60L * 60 * 24 * 365 * 5

            val random = Random.nextLong(RANDOM_RANGE)
            val initDeletedAt = STANDARD_DELETED_AT_TIME.minus(random, ChronoUnit.SECONDS)

            val parkingLotInfo = parkingLot.parkingLotInfo
            val parkingLotLocation = parkingLot.parkingLotLocation

            return ParkingLotEntity(
                id = parkingLot.parkingLotId,
                memberId = parkingLot.memberId,
                name = parkingLotInfo.name,
                fullAddress = parkingLotInfo.fullAddress,
                guName = parkingLotLocation.guName,
                cityName = parkingLotLocation.cityName,
                totalSpace = parkingLotInfo.totalSpace,
                occupiedSpace = parkingLotInfo.occupiedSpace,
                cost = parkingLotInfo.cost,
                extraCost = parkingLotInfo.extraCost,
                deletedAt = deletedAt ?: initDeletedAt
            )
        }
    }
}

