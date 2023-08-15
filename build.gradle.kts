import net.civmc.civgradle.CivGradleExtension

plugins {
    id("net.civmc.civgradle") version "2.+" apply false
    id("xyz.jpenilla.run-paper") version "2.1.0"
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "net.civmc.civgradle")

    configure<CivGradleExtension> {
        pluginName = project.property("pluginName") as String
    }

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.civmc.net/repository/maven-public")
        maven("https://repo.dmulloy2.net/content/groups/public/")
    }
}
