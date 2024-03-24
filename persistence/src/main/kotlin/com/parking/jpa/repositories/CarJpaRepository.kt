package com.parking.jpa.repositories

import com.parking.jpa.entity.CarJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CarJpaRepository: JpaRepository<CarJpaEntity, Long>, CarJpaRepositoryCustom {
}

interface CarJpaRepositoryCustom {
    fun findAllByMemberId(memberId: Long): List<CarJpaEntity>
}