package com.parking.jpa.repositories

import com.parking.jpa.entity.NoShowEntity
import org.springframework.data.jpa.repository.JpaRepository

interface NoShowRepository: JpaRepository<NoShowEntity, Long>, CustomNoShowRepository {
}

interface CustomNoShowRepository {
    fun findRecentlyNoShow(memberId: Long, carId: Long): NoShowEntity?
}