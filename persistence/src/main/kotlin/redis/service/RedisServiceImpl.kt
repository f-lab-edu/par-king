package redis.service

import org.redisson.api.RedissonClient
import org.springframework.stereotype.Service

@Service
class RedisServiceImpl(
    val redissonClient : RedissonClient
) : RedisService {
    override fun get(key: String): String {
        val bucket = redissonClient.getBucket<String>(key)

        return bucket.get()
    }

    override fun set(key: String, value: String) {
        val bucket = redissonClient.getBucket<String>(key)

        bucket.set(value)
    }
}