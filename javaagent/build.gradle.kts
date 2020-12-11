plugins {
    kotlin("jvm")
}

group = "com.epam"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("org.slf4j:slf4j-simple:1.7.30")
    implementation( "org.javassist:javassist:3.27.0-GA")

}

val jar = tasks.withType(Jar::class) {
    manifest {
        attributes["Manifest-Version"] = "1.0"
        attributes["PreMain-Class"] = "com.epam.Main"
        attributes["Can-Redefine-Classes"] = "true"
        attributes["Can-Retransform-Classes"] = "true"
        attributes["Can-Set-Native-Method-Prefix"] = "true"
    }
}
