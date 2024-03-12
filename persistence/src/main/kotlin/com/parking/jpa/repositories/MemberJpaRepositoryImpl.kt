package com.parking.jpa.repositories

import com.parking.jpa.entity.MemberJpaEntity
import com.parking.jpa.entity.QMemberJpaEntity.Companion.memberJpaEntity

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class MemberJpaRepositoryImpl : QuerydslRepositorySupport(MemberJpaEntity::class.java), MemberJpaRepositoryCustom {
    override fun findMemberByMemberId(memberId: String): MemberJpaEntity? {
        return from(memberJpaEntity)
            .where(memberJpaEntity.memberId.eq(memberId))
            .fetchFirst() ?: null
    }
}