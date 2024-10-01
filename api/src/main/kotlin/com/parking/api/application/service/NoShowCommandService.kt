package com.parking.api.application.service

import com.parking.api.adapter.out.*
import com.parking.api.application.port.`in`.noShow.SaveNoShowUseCase
import com.parking.domain.entity.DibsOnParkingLot
import com.parking.domain.entity.NoShow
import com.parking.domain.entity.NoShow.Companion.NO_SHOW_LIMIT_SECOND
import com.parking.domain.exception.MemberException
import com.parking.domain.exception.NoShowException
import com.parking.domain.exception.enum.ExceptionCode.*
import com.parking.redis.lock.RedisLock
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDateTime

@Service
class NoShowCommandService(
    private val memberInquiryAdapter: MemberInquiryAdapter,
    private val memberCommandAdapter: MemberCommandAdapter,
    private val noShowInquiryAdapter: NoShowInquiryAdapter,
    private val noShowCommandAdapter: NoShowCommandAdapter,
    private val dibsOnParkingLotCommandAdapter: DibsOnParkingLotCommandAdapter
): SaveNoShowUseCase {

    @Transactional
    @RedisLock("memberId")
    override fun save(memberId: Long, dibsOnParkingLot: DibsOnParkingLot) {
        val member = memberInquiryAdapter.findById(memberId) ?: throw MemberException(
            MEMBER_NOT_FOUND,
            MEMBER_NOT_FOUND.message
        )

        member.makeNoShow()

        val recentNoShow = noShowInquiryAdapter.findRecentlyNoShow(member.id!!, dibsOnParkingLot.carId)
        val currentTime = LocalDateTime.now()

        //가장 최근 noShow time 과 현재 시간을 비교해서 15분 이내에 No Show 가 된 적이 있으면 잘못 요청된 NoShow 이다
        recentNoShow?.let {
            val duration = Duration.between(it.noShowTime, currentTime)

            //같은 memberId, carId 로 limit 시간 안에 또 다른 요청이 오는것은 invalid 요청이므로 Exception 발생
            if (duration.seconds <= NO_SHOW_LIMIT_SECOND) {
                throw NoShowException(
                    NO_SHOW_INVALID_REQUEST,
                    NO_SHOW_INVALID_REQUEST.message
                )
            }
        }

        val newNoShow =
            NoShow(
                memberId = member.id!!,
                parkingLotId = dibsOnParkingLot.parkingLotId,
                carId = dibsOnParkingLot.carId,
                noShowTime = LocalDateTime.now()
            )

        dibsOnParkingLot.changeNoShow()

        memberCommandAdapter.saveMember(member)
        noShowCommandAdapter.save(newNoShow)
        dibsOnParkingLotCommandAdapter.save(dibsOnParkingLot)
    }
}