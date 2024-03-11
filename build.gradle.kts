import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    extra["springBootVersion"] = "3.2.2"
    extra["kotlinVersion"] = "1.9.22"

    val springBootVersion: String by extra
    val kotlinVersion: String by extra

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
        classpath("io.spring.gradle:dependency-management-plugin:1.0.6.RELEASE")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
        classpath("org.jetbrains.kotlin:kotlin-allopen:${kotlinVersion}")
        classpath("org.jetbrains.kotlin:kotlin-noarg:${kotlinVersion}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${kotlinVersion}}")
        classpath("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:2.8")
    }
}

allprojects {
    group = "par-king"
    version = "0.0.1-SNAPSHOT"

    apply {
        plugin("org.sonarqube")
    }

    repositories {
        getRepositories()
        mavenLocal()
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("java")
        plugin("kotlin")
        plugin("kotlin-spring")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("jacoco")
        plugin("groovy")
        plugin("kotlin-kapt")
        plugin("org.sonarqube")
    }

    val implementation by configurations
    val testImplementation by configurations

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-aop")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")
        implementation("io.netty:netty-resolver-dns-native-macos")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.2")
        implementation("com.fasterxml.jackson.module:jackson-module-afterburner")
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(module = "junit")
        }
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        testImplementation("org.mockito:mockito-junit-jupiter:2.23.0")
        testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")
        testImplementation("io.kotest:kotest-runner-junit5:4.6.1")
        testImplementation("io.mockk:mockk:1.12.0")
        testImplementation("io.kotest:kotest-property:4.6.1")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "21"
        }
    }

    val test by tasks.getting(Test::class) {
        useJUnitPlatform { }
    }
}