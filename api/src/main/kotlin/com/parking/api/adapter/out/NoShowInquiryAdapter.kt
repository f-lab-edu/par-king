package com.parking.api.adapter.out

import com.parking.api.application.port.out.FindNoShowPort
import com.parking.domain.entity.NoShow
import com.parking.jpa.repositories.NoShowRepository
import org.springframework.stereotype.Component

@Component
class NoShowInquiryAdapter(
    private val noShowRepository: NoShowRepository
): FindNoShowPort  {
    override fun findRecentlyNoShow(memberId: Long, carId: Long): NoShow? {
        return noShowRepository.findRecentlyNoShow(memberId, carId)?.to()
    }
}