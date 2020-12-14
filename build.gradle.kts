plugins {
    kotlin("jvm") version "1.4.20"
    application
}

group = "com.epam"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "http://oss.jfrog.org/oss-release-local")
    gradlePluginPortal()
    jcenter()
    maven(url = "https://dl.bintray.com/kotlin/kotlinx/")
    maven(url = "https://oss.jfrog.org/artifactory/list/oss-release-local")
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
        listOf("-javaagent:javaagent\\build\\libs\\JavaAgent-1.0-SNAPSHOT.jar")
}
val task = task("Sleep time") {
    didWork =false
    dependsOn(":JavaAgent:jar")
    didWork=true
}

//TODO Remove this task 
task("StartApp") {
    group = "application"
    dependsOn(task)
    while (!task.didWork) {

    }
    dependsOn("run")
}


