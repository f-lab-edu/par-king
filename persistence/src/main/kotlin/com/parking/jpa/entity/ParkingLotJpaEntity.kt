package com.parking.jpa.entity

import com.parking.domain.entity.ParkingLot
import com.parking.domain.entity.ParkingLotInfo
import com.parking.domain.entity.ParkingLotLocation
import jakarta.persistence.*
import java.math.BigDecimal


@Entity
@Table(name = "parking_lot")
data class ParkingLotJpaEntity(
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
    val extraCost: BigDecimal = BigDecimal.ZERO

) : BaseEntity() {
    fun to() = ParkingLot(
        parkingLotId = id,
        memberId = memberId,
        parkingLotInfo = ParkingLotInfo(name, fullAddress, totalSpace, occupiedSpace, cost, extraCost),
        parkingLotLocation = ParkingLotLocation(cityName, guName)
    )

    companion object {
        fun from(parkingLot: ParkingLot): ParkingLotJpaEntity {
            val parkingLotInfo = parkingLot.parkingLotInfo
            val parkingLotLocation = parkingLot.parkingLotLocation

            return ParkingLotJpaEntity(
                id = parkingLot.parkingLotId,
                memberId = parkingLot.memberId,
                name = parkingLotInfo.name,
                fullAddress = parkingLotInfo.fullAddress,
                guName = parkingLotLocation.guName,
                cityName = parkingLotLocation.cityName,
                totalSpace = parkingLotInfo.totalSpace,
                occupiedSpace = parkingLotInfo.occupiedSpace,
                cost = parkingLotInfo.cost,
                extraCost = parkingLotInfo.extraCost
            )
        }
    }
}

