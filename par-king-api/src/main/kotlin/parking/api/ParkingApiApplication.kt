package parking.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ParKingApiApplication

fun main(args: Array<String>) {
	System.setProperty("spring.config.name", "application-api")
	runApplication<ParKingApiApplication>(*args)
}
