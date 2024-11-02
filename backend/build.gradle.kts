plugins {
    kotlin("jvm")
    id("io.ktor.plugin") version "2.3.8"
    application
}

group = "org"
version = "1.0.0"
application {
    mainClass.set("org.ApplicationKt")
    val development = project.findProperty("io.ktor.development")?.toString() ?: "false"
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$development")
}

dependencies {
    implementation(project(":shared"))
    implementation("ch.qos.logback:logback-classic:1.2.10")
    implementation("io.ktor:ktor-server-core:2.3.8")
    implementation("io.ktor:ktor-server-netty:2.3.8")
    testImplementation("io.ktor:ktor-server-tests:2.3.8")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.8.0")
    implementation("com.opencsv:opencsv:5.7.1")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.8")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.8")
}
