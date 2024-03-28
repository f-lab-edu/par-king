package com.parking.jpa.repositories

import com.parking.jpa.entity.DibsOnParkingLotEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DibsOnParkingLotRepository: JpaRepository<DibsOnParkingLotEntity, Long> {
}