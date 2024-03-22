package com.parking.jpa.repositories

import com.parking.jpa.entity.ParkingLotJpaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ParkingLotJpaRepository : JpaRepository<ParkingLotJpaEntity, Long>, ParkingLotJpaRepositoryCustom

interface ParkingLotJpaRepositoryCustom{
    fun findAllByMemberId(memberId: Long, pageable: Pageable): Page<ParkingLotJpaEntity>

    fun findAllByLocation(cityName: String?, guName: String?, pageable: Pageable): Page<ParkingLotJpaEntity>
}