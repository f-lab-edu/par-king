package com.parking.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import com.parking.jpa.configuration.EnableDataSourceConfiguration
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication(
	exclude = [
		DataSourceAutoConfiguration::class
	],
	scanBasePackages = ["com.parking"]
)
@EnableDataSourceConfiguration
@ConfigurationPropertiesScan
@EnableScheduling
class ParKingApiApplication

fun main(args: Array<String>) {
	runApplication<ParKingApiApplication>(*args)
}
