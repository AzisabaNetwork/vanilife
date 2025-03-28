plugins {
    `java-library`
    kotlin("jvm") version "2.1.20-RC3"
    kotlin("plugin.serialization") version "2.1.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.16"
}

group = "net.azisaba"
version = "0.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("com.tksimeji:kunectron:1.0.0-beta.5")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.mariadb.jdbc:mariadb-java-client:3.5.2")
    implementation("com.charleskorn.kaml:kaml:0.73.0")
    implementation("com.zaxxer:HikariCP:6.2.1")
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
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
