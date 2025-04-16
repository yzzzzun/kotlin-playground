plugins {
    kotlin("jvm") version "1.9.23"
}

group = "com.hangkong"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.23")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}