package domain.entity

import java.time.LocalDateTime

data class NoShow(
    val memberId: Member,
    val parkingLotId: ParkingLot,
    val carId: Car,
    val noShowTime: LocalDateTime
)
