plugins {
    kotlin("jvm") version "2.1.20-RC3"
    kotlin("plugin.serialization") version "2.1.0"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "net.azisaba"
version = "0.0.0"

allprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.serialization")
    }

    repositories {
        mavenCentral()
        maven {
            name = "papermc"
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("com.charleskorn.kaml:kaml:0.73.0")
    }

    kotlin {
        jvmToolchain(21)
    }
}

subprojects {
    group = rootProject.gradle
    version = rootProject.version
}

dependencies {
    implementation(project(":modules:api"))
    implementation(project(":modules:core"))
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}

tasks.runServer {
    minecraftVersion("1.21.4")
}