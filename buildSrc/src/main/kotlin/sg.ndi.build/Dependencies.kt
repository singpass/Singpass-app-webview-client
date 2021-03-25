package sg.ndi.build

import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

    const val KOTLIN_STANDARD_LIB = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.Kotlin.KOTLIN}"
    const val ANDROIDX_WEBKIT = "androidx.webkit:webkit:${Versions.Androidx.WEBKIT}"

    const val ANDROIDX_TEST_UIAUTOMATOR = "androidx.test.uiautomator:uiautomator:${Versions.Androidx.UI_AUTOMATOR}"
    const val ANDROIDX_TEST_ESPRESSO = "androidx.test.espresso:espresso-core:${Versions.Androidx.ESPRESSO}"
    const val ANDROIDX_TEST_ESPRESSO_INTENTS = "androidx.test.espresso:espresso-intents:${Versions.Androidx.ESPRESSO}"
    const val ANDROIDX_TEST_CORE_KTX = "androidx.test:core-ktx:${Versions.Androidx.TEST_CORE}"
    const val ANDROIDX_TEST_RUNNER = "androidx.test:runner:${Versions.Androidx.TEST_CORE}"
    const val ANDROIDX_TEST_RULES = "androidx.test:rules:${Versions.Androidx.TEST_CORE}"
    const val ANDROIDX_TEST_EXTENSION_JUNIT_KTX = "androidx.test.ext:junit-ktx:${Versions.Androidx.TEST_EXTENSION}"
    const val ANDROIDX_TEST_EXTENSION_JUNIT = "androidx.test.ext:junit:${Versions.Androidx.TEST_EXTENSION}"

    object PROJECT {
        const val ANDROID_GRADLE_CLASSPATH = "com.android.tools.build:gradle:${Versions.ANDROID_GRADLE_PLUGIN}"
        const val KOTLIN_COMPILER_CLASSPATH = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin.KOTLIN}"
    }

    fun DependencyHandler.testing() {

        val testImplementation = "testImplementation"
        val androidTestImplementation = "androidTestImplementation"

        configure(configuration = testImplementation, depNamePath = ANDROIDX_TEST_EXTENSION_JUNIT)
        configure(configuration = testImplementation, depNamePath = ANDROIDX_TEST_EXTENSION_JUNIT_KTX)

        configure(configuration = androidTestImplementation, depNamePath = ANDROIDX_TEST_UIAUTOMATOR)
        configure(configuration = androidTestImplementation, depNamePath = ANDROIDX_TEST_ESPRESSO)
        configure(configuration = androidTestImplementation, depNamePath = ANDROIDX_TEST_ESPRESSO_INTENTS)
        configure(configuration = androidTestImplementation, depNamePath = ANDROIDX_TEST_CORE_KTX)
        configure(configuration = androidTestImplementation, depNamePath = ANDROIDX_TEST_RUNNER)
        configure(configuration = androidTestImplementation, depNamePath = ANDROIDX_TEST_RULES)
    }

}
