package com.parking.api.application.service

import com.parking.api.application.port.`in`.car.SaveCarUseCase
import com.parking.api.application.port.out.FindMemberPort
import com.parking.api.application.port.out.SaveCarPort
import com.parking.api.application.vo.RegisterCarVO
import com.parking.api.application.vo.ResponseCarInfoVO
import com.parking.domain.exception.MemberException
import com.parking.domain.exception.enum.ExceptionCode.MEMBER_NOT_FOUND
import org.springframework.stereotype.Service

@Service
class CarCommandService(
    private val findMemberPort: FindMemberPort,
    private val saveCarPort: SaveCarPort
): SaveCarUseCase {
    override fun save(carVO: RegisterCarVO): ResponseCarInfoVO {
        val id = findMemberPort.findIdByMemberId(carVO.memberId) ?: throw MemberException(
            MEMBER_NOT_FOUND,
            MEMBER_NOT_FOUND.message
        )

        val car = saveCarPort.save(carVO.toCar(id))

        return ResponseCarInfoVO.from(car, null)
    }
}