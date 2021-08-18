// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        mavenCentral()
        google()
        maven(url = "https://dl.bintray.com/kotlin/dokka")
    }
    dependencies {
        classpath(libs.android.gradle.plugin.classpath)
        classpath(libs.kotlin.gradle.plugin.classpath)
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
