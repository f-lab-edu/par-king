package com.parking.api.adapter.`in`

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController(
    @Value("\${hello.name}")
    val helloName: String
) {
    @GetMapping("/hello")
    fun hello(): String {
        return helloName
    }

    @GetMapping("/test/token2")
    fun token(): String {
        return "success token"
    }
}