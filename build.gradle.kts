plugins {
    java
    application
}

application {
    mainClass.set("Main")
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