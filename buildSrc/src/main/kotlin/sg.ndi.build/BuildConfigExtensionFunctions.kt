package sg.ndi.build

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.DefaultConfig
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

const val FLAVOR_DIMENSION_LANDSCAPE = "landscape"
const val FLAVOR_PRODUCTION = "prod"
const val FLAVOR_STAGING = "stg"

fun LibraryExtension.libraryBuildConfigs(
    project: Project,
    defaultConfigModifier: ((DefaultConfig) -> Unit)? = null
) {

    sourceSets {

        maybeCreate(FLAVOR_PRODUCTION).java.srcDirs("build/generated/source/buildConfig/$FLAVOR_PRODUCTION")
        maybeCreate(FLAVOR_STAGING).java.srcDirs("build/generated/source/buildConfig/$FLAVOR_STAGING")
    }

    compileSdkVersion(Versions.COMPILE_SDK_VERSION)

    flavorDimensions(FLAVOR_DIMENSION_LANDSCAPE)

    defaultConfig {
        defaultConfigModifier?.invoke(this)
        minSdk = Versions.MIN_ANDROID_SDK
        targetSdk = Versions.TARGET_ANDROID_SDK

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }

    productFlavors {
        register(FLAVOR_PRODUCTION) {
            dimension(FLAVOR_DIMENSION_LANDSCAPE)
            setStringBuildConfigField("SPM_APP_ID", "sg.ndi.sp")
        }

        register(FLAVOR_STAGING) {
            dimension(FLAVOR_DIMENSION_LANDSCAPE)
            setStringBuildConfigField("SPM_APP_ID", "sg.ndi.sp.dev")
        }
    }

    buildFeatures.run {
        buildConfig = true
    }

    compileOptions {
        // Sets Java compatibility to Java 8
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }

    project.tasks.withType(KotlinCompile::class.java).configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
            freeCompilerArgs = listOf(
                // Ignore: This declaration is experimental and its usage should be marked with "@kotlinx.coroutines.ExperimentalCoroutinesApi"
                // or "@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)"
                "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
                // Ignore: Its usage should be marked with "@kotlinx.coroutines.FlowPreview" or "@OptIn(kotlinx.coroutines.FlowPreview::class)"
                "-Xopt-in=kotlinx.coroutines.FlowPreview",
                "-Xopt-in=kotlin.RequiresOptIn"
            )
        }
    }
}
