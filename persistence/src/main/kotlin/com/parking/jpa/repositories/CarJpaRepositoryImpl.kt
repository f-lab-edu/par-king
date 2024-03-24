package com.parking.jpa.repositories

import com.parking.jpa.entity.CarJpaEntity
import org.springframework.stereotype.Repository
import com.parking.jpa.entity.QCarJpaEntity.Companion.carJpaEntity
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

@Repository
class CarJpaRepositoryImpl:  QuerydslRepositorySupport(CarJpaEntity::class.java), CarJpaRepositoryCustom {
    override fun findAllByMemberId(memberId: Long): List<CarJpaEntity> {
        return from(carJpaEntity)
            .where(carJpaEntity.memberId.eq(memberId))
            .fetch()
    }
}