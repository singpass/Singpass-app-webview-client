package sg.ndi.build

object Dependencies {

    const val KOTLIN_STANDARD_LIB = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.Kotlin.KOTLIN}"
    const val ANDROIDX_WEBKIT = "androidx.webkit:webkit:${Versions.Androidx.WEBKIT}"

    object PROJECT {
        const val ANDROID_GRADLE_CLASSPATH = "com.android.tools.build:gradle:${Versions.ANDROID_GRADLE_PLUGIN}"
        const val KOTLIN_COMPILER_CLASSPATH = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin.KOTLIN}"
    }
}
