package com.parking.api.application.port.out

import com.parking.domain.entity.NoShow

interface SaveNoShowPort {
    fun save(noShow: NoShow): NoShow
}