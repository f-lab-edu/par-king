package com.parking.domain.entity

import java.time.LocalDateTime

data class NoShow(
    var noShowId: Long? = null,
    val memberId: Long,
    val parkingLotId: Long,
    val carId: Long,
    val noShowTime: LocalDateTime
)
