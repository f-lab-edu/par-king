package domain.entity

import java.time.LocalDateTime

data class Car (
    val carId: Long? = null,
    var carNumber: String,
    val memberId: Long,
    var parkingLotId: Long? = null,
    val DibsOnParkingLotId: Long,
    var dibsOnParkingLotStatus: DibsOnParkingLotStatus = DibsOnParkingLotStatus.NORMAL,
    var startDibsOnTime: LocalDateTime? = null
)
