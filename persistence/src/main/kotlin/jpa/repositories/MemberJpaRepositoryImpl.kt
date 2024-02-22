package jpa.repositories

import jpa.entity.MemberJpaEntity
import jpa.entity.QMemberJpaEntity.Companion.memberJpaEntity
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class MemberJpaRepositoryImpl : QuerydslRepositorySupport(MemberJpaEntity::class.java), MemberJpaRepositoryCustom {
    override fun findMemberByMemberId(memberId: Long): MemberJpaEntity? {
        return from(memberJpaEntity)
            .where(memberJpaEntity.id.eq(memberId))
            .fetchFirst() ?: null
    }
}