package redis.service

interface RedisService {
    fun get(key: String) : String

    fun set(key: String, value: String)
}