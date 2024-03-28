package com.parking.jpa.repositories

import com.parking.jpa.entity.MemberEntity
import com.parking.jpa.entity.QMemberEntity.Companion.memberEntity

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class MemberRepositoryImpl : QuerydslRepositorySupport(MemberEntity::class.java), MemberRepositoryCustom {
    override fun findMemberByMemberId(memberId: String): MemberEntity? {
        return from(memberEntity)
            .where(memberEntity.memberId.eq(memberId))
            .fetchFirst() ?: null
    }

    override fun findIdByMemberId(memberId: String): Long? {
        return from(memberEntity)
            .where(memberEntity.memberId.eq(memberId))
            .select(memberEntity.id)
            .fetchFirst() ?: null
    }
}