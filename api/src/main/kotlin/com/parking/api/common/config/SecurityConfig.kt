package com.parking.api.common.config

import com.parking.api.common.jwt.JwtAuthenticationFilter
import com.parking.api.common.jwt.JwtTokenProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val entryPoint: AuthenticationEntryPoint,
    @Value("\${parking.secret.key}")
    private val secretKey: String
) {
    @Bean
    fun filterChain(http : HttpSecurity) : SecurityFilterChain {

        return http.csrf { it.disable() }
            .cors { it.disable() }
            .authorizeHttpRequests { request ->
                request.requestMatchers("/member/sign-in", "/hello", "/hello/*", "/member/sign-up", "/member/refresh")
                    .permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .logout {
                it.logoutSuccessUrl("/member/sign-in")
            }
            .addFilterBefore(JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling { it.authenticationEntryPoint(entryPoint) }.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        val pbkdf2PasswordEncoder = Pbkdf2PasswordEncoder(secretKey, 128, 1, SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256)
        pbkdf2PasswordEncoder.setEncodeHashAsBase64(true)

        return pbkdf2PasswordEncoder
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

}
