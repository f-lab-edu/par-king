package domain.entity

data class DibsOnParkingLot(
    val memberId: Member,
    val parkingLotId: ParkingLot,
    val carId: Car,
    var currentStatus: DibsOnParkingLotStatus = DibsOnParkingLotStatus.DIBS_ON_PARKING_LOT
)

enum class DibsOnParkingLotStatus {
    DIBS_ON_PARKING_LOT,
    COMPLETE,
    NO_SHOW,
    NORMAL
}