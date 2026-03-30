package buildlogic

import dev.kikugie.stonecutter.build.StonecutterBuildExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.the

// A huge thank you to KikuGie for sharing this trick and code

open class FabricLoomCompatPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val current = the<StonecutterBuildExtension>().current.parsed
        if (current > "26.0") setupNewLoomFacade() else setupOldLoomFacade()
        extensions.create<FabricExtensions>("fabric", this, current > "26.0")
    }

    private fun Project.setupNewLoomFacade() {
        plugins.apply("net.fabricmc.fabric-loom")

        val names = listOf("api", "implementation", "compileOnly", "runtimeOnly", "localRuntime")

        for (name in names) {
            val loomified = "mod" + name.replaceFirstChar(Char::uppercaseChar)
            val new = configurations.create(loomified)
            configurations.named(name) {
                extendsFrom(new)
            }
        }

        configurations.register("mappings") {
            isCanBeResolved = false
            isCanBeConsumed = false
        }
    }

    private fun Project.setupOldLoomFacade() {
        plugins.apply("net.fabricmc.fabric-loom-remap")
    }

    open class FabricExtensions(val project: Project, val isNew: Boolean) {
        val modJar: TaskProvider<Jar> by lazy {
            val candidate = if (isNew) project.tasks.named("jar")
            else project.tasks.named("remapJar")
            candidate as TaskProvider<Jar>
        }

        val modSourcesJar: TaskProvider<Jar> by lazy {
            val candidate = if (isNew) project.tasks.named("sourcesJar")
            else project.tasks.named("remapSourcesJar")
            candidate as TaskProvider<Jar>
        }
    }
}