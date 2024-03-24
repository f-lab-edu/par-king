package com.parking.api.application.service

import com.parking.api.adapter.out.CarCommandAdapter
import com.parking.api.adapter.out.MemberInquiryAdapter
import com.parking.api.application.port.`in`.car.SaveCarUseCase
import com.parking.api.application.vo.RegisterCarVO
import com.parking.api.application.vo.ResponseCarInfoVO
import com.parking.domain.exception.MemberException
import com.parking.domain.exception.enum.ExceptionCode.MEMBER_NOT_FOUND
import org.springframework.stereotype.Service

@Service
class CarCommandService(
    private val memberInquiryAdapter: MemberInquiryAdapter,
    private val carCommandAdapter: CarCommandAdapter
): SaveCarUseCase {
    override fun save(carVO: RegisterCarVO): ResponseCarInfoVO {
        val id = memberInquiryAdapter.findIdByMemberId(carVO.memberId) ?: throw MemberException(
            MEMBER_NOT_FOUND,
            MEMBER_NOT_FOUND.message
        )

        val car = carCommandAdapter.save(carVO.toCar(id))

        return ResponseCarInfoVO.from(car, null)
    }
}