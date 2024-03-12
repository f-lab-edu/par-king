package com.parking.domain.entity

import java.time.LocalDateTime

data class Member(
    val id: Long? = null,
    val memberInfo: MemberInfo,
    var password: String? = null,
    val carIdList: List<Car> = listOf(),
    val parkingLotIdList: List<ParkingLot> = listOf(),
    val dibsOnParkingLotList: List<DibsOnParkingLot> = listOf(),
    val noShowList: List<NoShow> = listOf(),
    var noShowCount: Long = 0L,
    var startNoShowTime: LocalDateTime? = null,
    var memberStatus: MemberStatus = MemberStatus.ACTIVATED
) {
    companion object {
        const val LIMIT_PASSWORD_TRY_COUNT = 5
    }
    fun getMemberId(): String {
        return this.memberInfo.memberId
    }
}

data class MemberInfo(
    val memberId: String,
    val name: String,
    val email: String? = null
)

enum class MemberStatus {
    ACTIVATED,
    REVOKED
}
