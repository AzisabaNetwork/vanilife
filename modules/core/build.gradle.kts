plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

dependencies {
    compileOnly("com.tksimeji:kunectron:1.0.0-beta.5")
    implementation(project(":modules:api"))
    implementation("org.mariadb.jdbc:mariadb-java-client:3.5.2")
    implementation("com.zaxxer:HikariCP:6.2.1")
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
    dependsOn("shadowJar")
    minecraftVersion("1.21.4")
}