package com.parking.jpa.entity

import com.parking.domain.entity.NoShow
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "no_show")
data class NoShowEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "member_id")
    val memberId: Long,

    @Column(name = "car_id")
    val carId: Long,

    @Column(name = "parking_lot_id")
    val parkingLotId: Long,

    @Column(name = "no_show_time")
    val noShowTime: LocalDateTime = LocalDateTime.now()

) : BaseEntity() {
    fun to() = NoShow(
        noShowId = id,
        memberId,
        parkingLotId,
        carId,
        noShowTime
    )

    companion object {
        fun from(noShow: NoShow) = NoShowEntity(
            id = noShow.noShowId,
            memberId = noShow.memberId,
            carId = noShow.carId,
            parkingLotId = noShow.parkingLotId,
            noShowTime = noShow.noShowTime
        )
    }
}
