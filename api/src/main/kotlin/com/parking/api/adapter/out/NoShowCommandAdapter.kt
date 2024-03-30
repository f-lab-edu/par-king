package com.parking.api.adapter.out

import com.parking.api.application.port.out.SaveNoShowPort
import com.parking.domain.entity.NoShow
import com.parking.jpa.entity.NoShowEntity
import com.parking.jpa.repositories.NoShowRepository
import org.springframework.stereotype.Component

@Component
class NoShowCommandAdapter(
    private val noShowRepository: NoShowRepository
): SaveNoShowPort {
    override fun save(noShow: NoShow): NoShow {
        return noShowRepository.save(NoShowEntity.from(noShow)).to()
    }
}