/*
 * Kotlin module for side-by-side comparison with Java examples.
 */

plugins {
    application
    id("org.jetbrains.kotlin.jvm") version "2.2.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation(libs.guava)
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass = "startup.App"
}

val runClasspath = objects.fileCollection().from(
    layout.buildDirectory.dir("classes/kotlin/main"),
    layout.buildDirectory.dir("classes/java/main"),
    layout.buildDirectory.dir("resources/main"),
    configurations.runtimeClasspath
)

tasks.register<JavaExec>("runEx01") {
    group = "application"
    description = "Runs basic.ex01 (Kotlin)"
    dependsOn("classes")
    classpath = runClasspath
    mainClass.set("basic.ex01")
}

val weekPackages = mapOf(
    "01" to "basic",
    "02" to "arraymethod",
    "03" to "oop1",
    "04" to "oop2",
    "05" to "exceptionapi",
    "06" to "collection",
    "07" to "modernjava",
    "08" to "fileio",
    "09" to "threading",
    "10" to "network"
)

weekPackages.forEach { (week, pkg) ->
    (1..3).forEach { ex ->
        val exNo = ex.toString().padStart(2, '0')
        tasks.register<JavaExec>("runWeek${week}Ex${exNo}") {
            group = "application"
            description = "Runs $pkg.ex$exNo (Kotlin)"
            dependsOn("classes")
            classpath = runClasspath
            mainClass.set("$pkg.ex$exNo")
        }
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
