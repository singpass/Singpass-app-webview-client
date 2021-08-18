plugins {
    `kotlin-dsl`
}
repositories {
    mavenCentral()
    google()
}
dependencies {
    implementation(libs.android.gradle.plugin.classpath)
    implementation(libs.kotlin.gradle.plugin.classpath)
    implementation(libs.kotlin.reflect)
}
