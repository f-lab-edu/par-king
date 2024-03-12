package com.parking.api.util

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordUtil(
    private val passwordEncoder: PasswordEncoder
) {
    fun encode(plainText: String) : String {
        return passwordEncoder.encode(plainText)
    }

    fun matches(plainText: String, encodedText: String) : Boolean {
        return passwordEncoder.matches(plainText, encodedText)
    }
}