package com.parking.jpa.repositories

import com.parking.jpa.entity.DibsOnParkingLotJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DibsOnParkingLotJpaRepository: JpaRepository<DibsOnParkingLotJpaEntity, Long> {
}