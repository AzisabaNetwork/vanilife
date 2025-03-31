plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

dependencies {
    compileOnly("com.tksimeji:kunectron:1.0.0-beta.5")
    compileOnly(project(":modules:api"))
    implementation("org.mariadb.jdbc:mariadb-java-client:3.5.2")
    implementation("com.zaxxer:HikariCP:6.2.1")
}