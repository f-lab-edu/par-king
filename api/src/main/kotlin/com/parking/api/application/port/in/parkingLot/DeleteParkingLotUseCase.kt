package com.parking.api.application.port.`in`.parkingLot

interface DeleteParkingLotUseCase {
    fun delete(parkingLotId: Long)
}