import sg.ndi.build.FLAVOR_PRODUCTION
import sg.ndi.build.FLAVOR_STAGING
import sg.ndi.build.libraryBuildConfigs

plugins {
    id(libs.plugins.android.library.module.get().pluginId)
    kotlin(libs.plugins.kotlin.android.get().pluginId)
    kotlin(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.maven.publish.get().pluginId)
    id(libs.plugins.signing.get().pluginId)
    alias(libs.plugins.jetbrains.dokka)
}

ext {
    set("PUBLISH_GROUP_ID", "io.github.singpass")
    set("PUBLISH_VERSION", "1.2.0")
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
    implementation(libs.androidx.webkit)
    testImplementation(libs.bundles.test.impl)
    androidTestImplementation(libs.bundles.android.test.impl)
}
