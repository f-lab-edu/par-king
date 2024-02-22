package jpa.entity

import domain.entity.NoShow
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "no_show")
data class NoShowJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

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
}
