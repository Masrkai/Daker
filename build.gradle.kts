plugins {
    java
    application
}

application {
    mainClass.set("Main") // default gradle run target
}

sourceSets {
    main {
        java {
            setSrcDirs(listOf("src"))
        }
    }
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

// Additional task for the GUI
tasks.register<JavaExec>("runGUI") {
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("ClubManagementGUI")
}