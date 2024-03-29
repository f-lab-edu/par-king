package com.parking.jpa.repositories

import com.parking.jpa.entity.CarEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CarRepository: JpaRepository<CarEntity, Long>, CarRepositoryCustom {
}

interface CarRepositoryCustom {
    fun findAllByMemberId(memberId: Long): List<CarEntity>
}