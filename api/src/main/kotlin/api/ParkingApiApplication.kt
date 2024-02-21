package api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import parking.jpa.configuration.EnableDataSourceConfiguration

@SpringBootApplication(
	exclude = [
		DataSourceAutoConfiguration::class
	]
)
@EnableDataSourceConfiguration
@ConfigurationPropertiesScan
class ParKingApiApplication

fun main(args: Array<String>) {
	runApplication<ParKingApiApplication>(*args)
}
