import sg.ndi.build.Dependencies.testing
import sg.ndi.build.FLAVOR_PRODUCTION
import sg.ndi.build.FLAVOR_STAGING
import sg.ndi.build.libraryBuildConfigs

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka") version "1.4.30"
}

ext {
    set("PUBLISH_GROUP_ID", "io.github.singpass")
    set("PUBLISH_VERSION", "1.0.0")
    set("PUBLISH_ARTIFACT_ID", "singpass-webview-client")
    set("STAGING_PUBLISH_ARTIFACT_ID", "singpass-webview-client-staging")
    set("aarName", "SpWebViewClientLib")
}

apply(from = "$rootDir/publish-mavencentral.gradle")

android {
    libraryBuildConfigs(project = project)

    libraryVariants.all {
        outputs.map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
            .forEach {
                if (name.contains("release", ignoreCase = true)) {

                    if (name.contains(FLAVOR_PRODUCTION, ignoreCase = true)) {
                        it.outputFileName = "${extra.get("aarName")}.aar"
                    } else {
                        it.outputFileName = "${extra.get("aarName")}-$FLAVOR_STAGING.aar"
                    }
                } else
                    it.outputFileName = "${extra.get("aarName")}-$name.aar"
            }
    }
}

dependencies {

    implementation(sg.ndi.build.Dependencies.KOTLIN_STANDARD_LIB)
    implementation(sg.ndi.build.Dependencies.ANDROIDX_WEBKIT)
    testing()
}
