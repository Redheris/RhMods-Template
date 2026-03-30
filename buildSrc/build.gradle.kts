plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    maven("https://maven.kikugie.dev/snapshots")
}

dependencies {
    compileOnly("dev.kikugie:stonecutter:0.9")
}

gradlePlugin {
    plugins {
        create("fabricFacade") {
            id = "fabric-loom-compat"
            implementationClass = "buildlogic.FabricLoomCompatPlugin"
        }
    }
}
