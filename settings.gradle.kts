pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9"
}

stonecutter {
    create(rootProject) {
        fun fabric(vararg versions: String) {
            versions(versions.toList()).buildscript("build.fabric.gradle.kts")
        }
        // See https://stonecutter.kikugie.dev/wiki/start/#choosing-minecraft-versions
//        versions("1.21.8", "1.21.11").buildscript("build.fabric_remap.gradle.kts")
//        version("26.1", "26.1").buildscript("build.fabric.gradle.kts")
        fabric("1.21.8", "1.21.11", "26.1")
        vcsVersion = "1.21.8"
    }
}

rootProject.name = "Template"