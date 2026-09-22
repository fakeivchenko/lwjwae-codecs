plugins {
    id("java-library")
    id("maven-publish")
    id("checkstyle")
    id("io.freefair.lombok")
    id("com.diffplug.spotless")
}

description = "JSON for the typed bridge of lwjwae through Jakarta JSON Binding; the application picks an implementation."

val lwjwaeVersion = providers.gradleProperty("lwjwaeVersion").get()

dependencies {
    // lwjwae
    api("dev.ivchenko.lwjwae:lwjwae-core:$lwjwaeVersion")

    // Jakarta JSON Binding: the API only, the application picks an implementation (Yasson, ...)
    api("jakarta.json.bind:jakarta.json.bind-api:3.0.1")
    testRuntimeOnly("org.eclipse:yasson:3.0.4")

    // lwjwae test fixtures, and the backend of this machine for the display tests
    testImplementation(testFixtures("dev.ivchenko.lwjwae:lwjwae-core:$lwjwaeVersion"))
    testRuntimeOnly("dev.ivchenko.lwjwae:lwjwae-gtk:$lwjwaeVersion")
    testRuntimeOnly("dev.ivchenko.lwjwae:lwjwae-windows:$lwjwaeVersion")
    testRuntimeOnly("dev.ivchenko.lwjwae:lwjwae-macos:$lwjwaeVersion")

    // JUnit
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
