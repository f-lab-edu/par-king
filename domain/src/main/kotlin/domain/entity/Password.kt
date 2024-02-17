package domain.entity

data class Password (
    var passwordId: Long? = null,
    val memberId: Long,
    val password: String
)
