package com.parking.redis.service

import java.time.Duration

interface RedisService<T> {
    fun get(key: String) : T

    fun set(key: String, value: T, duration: Duration = Duration.ofMinutes(10L))

    fun getStringValues(pattern: String): List<String>

    fun deleteStringValues(pattern: String)
}