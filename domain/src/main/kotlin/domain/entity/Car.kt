package domain.entity

import java.time.LocalDateTime

data class Car (
    val carId: Long? = null,
    var carNumber: String,
    val memberId: Long,
    var parkingLotId: Long? = null,
    val dibsOnParkingLotId: Long? = null,
    var dibsOnParkingLotStatus: DibsOnParkingLotStatus? = null,
    var startDibsOnTime: LocalDateTime? = null
)
