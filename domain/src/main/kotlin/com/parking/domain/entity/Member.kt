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

    fun modifyMemberInfo(name: String, email: String?) {
        memberInfo.name = name
        memberInfo.email = email
    }

    fun revoke() {
        memberStatus = MemberStatus.REVOKED
    }

    fun isRevoked(): Boolean {
        return memberStatus == MemberStatus.REVOKED
    }
}

data class MemberInfo(
    val memberId: String,
    var name: String,
    var email: String? = null
)

enum class MemberStatus {
    ACTIVATED,
    REVOKED
}
