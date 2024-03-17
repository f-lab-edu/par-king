package com.parking.jpa.repositories

import com.parking.jpa.entity.MemberJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaRepository : JpaRepository<MemberJpaEntity, Long>, MemberJpaRepositoryCustom {
}

interface MemberJpaRepositoryCustom {
    fun findMemberByMemberId(memberId: String): MemberJpaEntity?

    fun findIdByMemberId(memberId: String): Long?
}