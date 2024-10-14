package com.parking.api.adapter.common

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

object StringUtil {
    val objectMapper = ObjectMapper().registerKotlinModule()
    inline fun <reified T : Any> String.toObjectList(): List<T> = objectMapper.readerForListOf(T::class.java).readValue(this)
}