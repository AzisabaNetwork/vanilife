plugins {
    kotlin("jvm") version "2.1.20-RC3"
    kotlin("plugin.serialization") version "2.1.0"
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
        maven {
            url = uri("https://repo.codemc.io/repository/maven-releases/")
        }
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
        compileOnly("com.github.retrooper:packetevents-spigot:2.7.0")
        compileOnly("com.tksimeji:kunectron:1.0.0-beta.7")
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
    implementation(project(":modules:core"))
}

tasks.build {
    dependsOn("shadowJar")
}