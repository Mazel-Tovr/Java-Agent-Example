plugins {
    kotlin("multiplatform")
}

group = "com.epam"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven (url ="https://dl.bintray.com/jetbrains/kotlin-native-dependencies")

}


kotlin {
    /* Targets configuration omitted. 
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlin:kotlin-native-gradle-plugin:1.3.0-rc-146")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }
    mingwX64("native") {
        binaries {
            executable()
        }
    }
}
