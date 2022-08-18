import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    application
}

group = "inc.evil"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("io.mockk:mockk:1.12.5")
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.4.1") // for kotest core jvm assertions
    testImplementation("io.kotest:kotest-property-jvm:5.4.1") // for kotest property test
    testImplementation("io.kotest:kotest-runner-console-jvm:4.1.3.2") //for gutter |> icons w/ kotest plugin
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.4.2")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.4.1")
    testImplementation("org.mockito:mockito-core:4.6.1")
    testImplementation("org.mockito:mockito-inline:4.6.1")
    testImplementation("org.mockito:mockito-junit-jupiter:4.7.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}
