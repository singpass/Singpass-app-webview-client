plugins {
    `kotlin-dsl`
}
repositories {
    mavenCentral()
    google()
    jcenter()
}
dependencies {
    // Todo: Find some way to make this versions also be able to read from one place
    // android gradle plugin, required by custom plugin
    implementation("com.android.tools.build:gradle:4.1.3")

    // kotlin plugin, required by custom plugin
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.31")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.31")
}
