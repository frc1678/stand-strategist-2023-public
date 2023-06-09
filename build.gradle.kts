import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization")
    id("org.jmailen.kotlinter") version "3.13.0"
}

group = "org.citruscircuits"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        @Suppress("UNUSED_VARIABLE") val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.8.0")
                implementation("io.github.evanrupert:excelkt:1.0.2")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Stand Strategist"
            packageVersion = "1.0.0"
            windows {
                iconFile.set(project.file("src/jvmMain/resources").resolve("app_icon/icon.ico"))
            }
            macOS {
                iconFile.set(project.file("src/jvmMain/resources").resolve("app_icon/icon.icns"))
            }
            linux {
                iconFile.set(project.file("src/jvmMain/resources").resolve("app_icon/icon.png"))
            }
        }
    }
}
