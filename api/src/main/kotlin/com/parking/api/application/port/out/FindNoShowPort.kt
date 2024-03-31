package com.parking.api.application.port.out

import com.parking.domain.entity.NoShow

interface FindNoShowPort {
    fun findRecentlyNoShow(memberId: Long, carId: Long): NoShow?
}