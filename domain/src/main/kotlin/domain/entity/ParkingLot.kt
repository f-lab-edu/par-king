package domain.entity

import java.math.BigDecimal

data class ParkingLot(
    var parkingLotId: Long? = null,
    val memberId: Long,
    val parkingLotInfo: ParkingLotInfo,
    val parkingLotLocation: ParkingLotLocation
)

data class ParkingLotInfo(
    val name: String,
    val fullAddress: String,
    val totalSpace: Long,
    val occupiedSpace: Long = 0L,
    val cost: BigDecimal = BigDecimal.ZERO,
    val extraCost: BigDecimal = BigDecimal.ZERO
)

data class ParkingLotLocation(
    val cityName: String,
    val guName: String
)
