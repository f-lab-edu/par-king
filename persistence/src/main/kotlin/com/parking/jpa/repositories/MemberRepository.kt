package com.parking.jpa.repositories

import com.parking.jpa.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<MemberEntity, Long>, MemberRepositoryCustom {
}

interface MemberRepositoryCustom {
    fun findMemberByMemberId(memberId: String): MemberEntity?

    fun findIdByMemberId(memberId: String): Long?
}