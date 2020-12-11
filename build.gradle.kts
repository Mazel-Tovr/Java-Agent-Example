plugins {
    kotlin("jvm") version "1.4.20"
    application
}

group = "com.epam"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("org.slf4j:slf4j-simple:1.7.30")
    //implementation("javassist:javassist:3.12.1.GA")
    implementation( "org.javassist:javassist:3.27.0-GA")

}

application {
    mainClassName = "com.epam.MainKt"
    applicationDefaultJvmArgs =
        listOf("-javaagent:D:\\GitHub\\Temp\\Java-Agent-Example\\javaagent\\build\\libs\\JavaAgent-1.0-SNAPSHOT.jar")
}
val task = task("Sleep time") {
    didWork =false
    dependsOn(":JavaAgent:jar")
    didWork=true
}

task("StartApp") {
    group = "application"
    dependsOn(task)
    while (!task.didWork) {

    }
    dependsOn("run")
}


