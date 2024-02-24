package redis.configuration

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class RedisConfig(
    @Value("\${redis.host}")
    val host: String,

    @Value("\${redis.port}")
    val port: String
) {
    companion object {
        const val PRE_FIX = "redis://"
    }
    @Profile(value = ["local"])
    @Bean
    fun localRedissonClient(): RedissonClient {
        val localAddress = "%s%s:%s".format(PRE_FIX, host, port)

        val config = Config()
        config.useSingleServer()
            .setAddress(localAddress)

        val redisson: RedissonClient = Redisson.create(config)

        return redisson
    }

    @Profile(value = ["!local"])
    @Bean
    fun redissonClient(): RedissonClient {
        val localAddress = "%s%s:%s".format(PRE_FIX, host, port)

        val config = Config()
        config.useSingleServer()
            .setAddress(localAddress)

        val redisson: RedissonClient = Redisson.create(config)

        return redisson
    }
}