import sg.ndi.build.Dependencies.testing
import sg.ndi.build.libraryBuildConfigs

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    libraryBuildConfigs(project = project)

    libraryVariants.all {
        outputs.map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
            .forEach {
                if (name.contains("release", ignoreCase = true))
                    it.outputFileName = "SpWebViewClientLib.aar"
                else
                    it.outputFileName = "SpWebViewClientLib-$name.aar"
            }
    }
}

dependencies {

    implementation(sg.ndi.build.Dependencies.KOTLIN_STANDARD_LIB)
    implementation(sg.ndi.build.Dependencies.ANDROIDX_WEBKIT)

    testing()
}
