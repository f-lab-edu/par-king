package domain.entity

import java.time.LocalDateTime

data class Member(
    val memberId: Long? = null,
    val memberInfo: MemberInfo,
    val password: Password? = null,
    val carIdList: List<Car> = listOf(),
    val parkingLotIdList: List<ParkingLot> = listOf(),
    val dibsOnParkingLotList: List<DibsOnParkingLot> = listOf(),
    val noShowList: List<NoShow> = listOf(),
    var noShowCount: Long = 0L,
    var startNoShowTime: LocalDateTime? = null,
    var memberStatus: MemberStatus = MemberStatus.ACTIVATED
)

data class MemberInfo(
    val memberId: String,
    val name: String,
    val email: String? = null
)

enum class MemberStatus {
    ACTIVATED,
    REVOKED
}
