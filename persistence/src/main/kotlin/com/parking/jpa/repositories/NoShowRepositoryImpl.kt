package com.parking.jpa.repositories

import com.parking.jpa.entity.NoShowEntity
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import com.parking.jpa.entity.QNoShowEntity.Companion.noShowEntity

@Repository
class NoShowRepositoryImpl: QuerydslRepositorySupport(NoShowEntity::class.java), CustomNoShowRepository {
    override fun findRecentlyNoShow(memberId: Long, carId: Long): NoShowEntity? {
        return from(noShowEntity)
            .where(noShowEntity.memberId.eq(memberId)
                .and(noShowEntity.carId.eq(carId)))
            .orderBy(noShowEntity.id.desc())
            .fetchFirst()
    }
}