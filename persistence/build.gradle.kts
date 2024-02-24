import org.gradle.api.tasks.bundling.Jar
import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

apply {
    plugin("kotlin-jpa")
}

allOpen {
    annotation("javax.persistence.Entity")
}

val queryDslVersion = "5.0.0"

dependencies {
    implementation ("com.mysql:mysql-connector-j")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.redisson:redisson-spring-boot-starter:3.23.3")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
//    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.querydsl:querydsl-jpa:${queryDslVersion}:jakarta")
    implementation("com.querydsl:querydsl-core:${queryDslVersion}")
    kapt("com.querydsl:querydsl-apt:${queryDslVersion}:jakarta")
    kapt("com.querydsl:querydsl-kotlin-codegen:${queryDslVersion}")
    implementation(project(":domain"))
}