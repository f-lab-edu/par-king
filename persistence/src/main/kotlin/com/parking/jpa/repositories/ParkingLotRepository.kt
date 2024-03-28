package com.parking.jpa.repositories

import com.parking.jpa.entity.ParkingLotEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ParkingLotRepository : JpaRepository<ParkingLotEntity, Long>, ParkingLotRepositoryCustom

interface ParkingLotRepositoryCustom{
    fun findAllByMemberId(memberId: Long, pageable: Pageable): Page<ParkingLotEntity>

    fun findAllByLocation(cityName: String?, guName: String?, pageable: Pageable): Page<ParkingLotEntity>
}