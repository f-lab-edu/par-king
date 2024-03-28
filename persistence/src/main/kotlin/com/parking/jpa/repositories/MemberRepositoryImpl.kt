package com.parking.jpa.repositories

import com.parking.jpa.entity.MemberEntity
import com.parking.jpa.entity.QMemberJpaEntity.Companion.memberJpaEntity

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class MemberRepositoryImpl : QuerydslRepositorySupport(MemberEntity::class.java), MemberRepositoryCustom {
    override fun findMemberByMemberId(memberId: String): MemberEntity? {
        return from(memberJpaEntity)
            .where(memberJpaEntity.memberId.eq(memberId))
            .fetchFirst() ?: null
    }

    override fun findIdByMemberId(memberId: String): Long? {
        return from(memberJpaEntity)
            .where(memberJpaEntity.memberId.eq(memberId))
            .select(memberJpaEntity.id)
            .fetchFirst() ?: null
    }
}