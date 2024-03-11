package jpa.repositories

import jpa.entity.MemberJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaRepository : JpaRepository<MemberJpaEntity, Long>, MemberJpaRepositoryCustom {
}

interface MemberJpaRepositoryCustom {
    fun findMemberByMemberId(memberId: String): MemberJpaEntity?
}