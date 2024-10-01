package com.parking.domain.entity

data class DibsOnParkingLot(
    var dibsOnParkingLotId: Long? = null,
    val memberId: Long,
    val parkingLotId: Long,
    val carId: Long,
    var currentStatus: DibsOnParkingLotStatus = DibsOnParkingLotStatus.DIBS_ON_PARKING_LOT
) {
    fun changeNoShow() {
        this.currentStatus = DibsOnParkingLotStatus.NO_SHOW
    }
    companion object {
        const val NO_SHOW_LIMIT_MINUTES = 15L
    }
}

enum class DibsOnParkingLotStatus {
    //찜 한 상태
    DIBS_ON_PARKING_LOT,
    //입차 완료한 상태
    COMPLETE,
    NO_SHOW,
    //찜 가능한 상태 (입차 완료 후 출차 하거나 노쇼에서 일정 시간이 지난 상태) Car 에서 사용하는 Status
    NORMAL
}