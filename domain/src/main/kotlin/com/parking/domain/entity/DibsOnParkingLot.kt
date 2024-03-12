package com.parking.domain.entity

data class DibsOnParkingLot(
    var dibsOnParkingLotId: Long? = null,
    val memberId: Long,
    val parkingLotId: Long,
    val carId: Long,
    var currentStatus: DibsOnParkingLotStatus = DibsOnParkingLotStatus.DIBS_ON_PARKING_LOT
)

enum class DibsOnParkingLotStatus {
    DIBS_ON_PARKING_LOT,
    COMPLETE,
    NO_SHOW,
    NORMAL
}