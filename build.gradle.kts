// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(sg.ndi.build.Dependencies.PROJECT.ANDROID_GRADLE_CLASSPATH)
        classpath(sg.ndi.build.Dependencies.PROJECT.KOTLIN_COMPILER_CLASSPATH)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
