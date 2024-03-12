package com.parking.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import com.parking.jpa.configuration.EnableDataSourceConfiguration

@SpringBootApplication(
	exclude = [
		DataSourceAutoConfiguration::class
	],
	scanBasePackages = ["com.parking"]
)
@EnableDataSourceConfiguration
@ConfigurationPropertiesScan
class ParKingApiApplication

fun main(args: Array<String>) {
	runApplication<ParKingApiApplication>(*args)
}
