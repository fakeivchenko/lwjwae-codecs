plugins {
    id("java-library")
    id("maven-publish")
    id("checkstyle")
    id("io.freefair.lombok")
    id("com.diffplug.spotless")
}

description = "JSON for the typed bridge of lwjwae through Gson."

val lwjwaeVersion = providers.gradleProperty("lwjwaeVersion").get()

dependencies {
    // lwjwae
    api("dev.ivchenko.lwjwae:lwjwae-core:$lwjwaeVersion")

    // Gson
    api("com.google.code.gson:gson:2.13.2")

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
