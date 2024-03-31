package com.parking.api.application.schedule

import com.parking.api.adapter.out.DibsOnParkingLotInquiryAdapter
import com.parking.api.application.port.`in`.noShow.SaveNoShowUseCase
import com.parking.domain.entity.DibsOnParkingLot
import com.parking.domain.entity.DibsOnParkingLotStatus
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class NoShowSchedule(
    private val dibsOnParkingLotInquiryAdapter: DibsOnParkingLotInquiryAdapter,
    private val saveNoShowUseCase: SaveNoShowUseCase
) {

    // 1분 마다 NoShow 에 해당하는 Data 를 모아 NoShow 상태로 바꿔주는 스케쥴러
    @Scheduled(cron = "0 0/1 * * * *")
    fun findNoShowMember() {
        val overTime = LocalDateTime.now().minusMinutes(DibsOnParkingLot.NO_SHOW_LIMIT_MINUTES)

        val noShowTargetDataList = dibsOnParkingLotInquiryAdapter.findDibsOnDataByOverTimeAndStatus(
            overTime,
            DibsOnParkingLotStatus.DIBS_ON_PARKING_LOT
        )

        noShowTargetDataList.forEach {
            saveNoShowUseCase.save(it.memberId, it)
        }
    }
}