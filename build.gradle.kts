plugins {
    java
    application
    id("com.gradleup.shadow") version "9.4.1"
}

application {
    mainClass.set("Main")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.xerial:sqlite-jdbc:3.51.0.0")
}

sourceSets {
    main {
        java {
            setSrcDirs(listOf("src"))
        }
        resources {
            setSrcDirs(listOf("src"))
        }
    }
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.register<JavaExec>("runGUI") {
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("ClubManagementGUI")
}

// Fat jar for CLI (Main)
tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    archiveBaseName.set("Project-cli")
    archiveClassifier.set("")
    manifest {
        attributes["Main-Class"] = "Main"
    }
}

// Fat jar for GUI (ClubManagementGUI)
tasks.register<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJarGUI") {
    archiveBaseName.set("Project-gui")
    archiveClassifier.set("")
    from(sourceSets["main"].output)
    configurations = listOf(project.configurations["runtimeClasspath"])
    manifest {
        attributes["Main-Class"] = "ClubManagementGUI"
    }
}