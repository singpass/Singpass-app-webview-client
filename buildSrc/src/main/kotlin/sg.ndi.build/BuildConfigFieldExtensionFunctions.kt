package sg.ndi.build

import com.android.build.api.dsl.VariantDimension
import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.internal.dsl.DefaultConfig
import com.android.build.gradle.internal.dsl.ProductFlavor

//region VariantDimension
fun VariantDimension.setStringBuildConfigField(key: String, value: String) {
    this.buildConfigField("String", key, value.toBuildConfigField())
}

fun VariantDimension.setStringArrayBuildConfigField(key: String, value: Array<String>) {
    val sanitizedValue = "{${value.joinToString { it.toBuildConfigField() }}}"
    this.buildConfigField("String[]", key, sanitizedValue)
}

fun VariantDimension.setBooleanBuildConfigField(key: String, value: Boolean) {
    this.buildConfigField("boolean", key, value.toString())
}

fun VariantDimension.setLongBuildConfigField(key: String, value: Long) {
    this.buildConfigField("long", key, value.toString())
}

fun VariantDimension.setIntBuildConfigField(key: String, value: Int) {
    this.buildConfigField("int", key, value.toString())
}

fun VariantDimension.setIntArrayBuildConfigField(key: String, value: Array<Int>) {
    val sanitizedValue = "{${value.joinToString()}}"
    this.buildConfigField("int[]", key, sanitizedValue)
}
//endregion

//region DefaultConfig
fun DefaultConfig.setStringBuildConfigField(key: String, value: String) {
    this.buildConfigField("String", key, value.toBuildConfigField())
}

fun DefaultConfig.setStringArrayBuildConfigField(key: String, value: Array<String>) {
    val sanitizedValue = "{${value.joinToString { it.toBuildConfigField() }}}"
    this.buildConfigField("String[]", key, sanitizedValue)
}

fun DefaultConfig.setBooleanBuildConfigField(key: String, value: Boolean) {
    this.buildConfigField("boolean", key, value.toString())
}

fun DefaultConfig.setLongBuildConfigField(key: String, value: Long) {
    this.buildConfigField("long", key, value.toString())
}

fun DefaultConfig.setIntBuildConfigField(key: String, value: Int) {
    this.buildConfigField("int", key, value.toString())
}

fun DefaultConfig.setIntArrayBuildConfigField(key: String, value: Array<Int>) {
    val sanitizedValue = "{${value.joinToString()}}"
    this.buildConfigField("int[]", key, sanitizedValue)
}
//endregion

//region ApplicationVariant
fun ApplicationVariant.setStringBuildConfigField(key: String, value: String) {
    this.buildConfigField("String", key, value.toBuildConfigField())
}

fun ApplicationVariant.setStringArrayBuildConfigField(key: String, value: Array<String>) {
    val sanitizedValue = "{${value.joinToString { it.toBuildConfigField() }}}"
    this.buildConfigField("String[]", key, sanitizedValue)
}

fun ApplicationVariant.setBooleanBuildConfigField(key: String, value: Boolean) {
    this.buildConfigField("boolean", key, value.toString())
}

fun ApplicationVariant.setLongBuildConfigField(key: String, value: Long) {
    this.buildConfigField("long", key, value.toString())
}

fun ApplicationVariant.setIntBuildConfigField(key: String, value: Int) {
    this.buildConfigField("int", key, value.toString())
}

fun ApplicationVariant.setIntArrayBuildConfigField(key: String, value: Array<Int>) {
    val sanitizedValue = "{${value.joinToString()}}"
    this.buildConfigField("int[]", key, sanitizedValue)
}
//endregion

//region ProductFlavor
fun ProductFlavor.setStringBuildConfigField(key: String, value: String) {
    this.buildConfigField("String", key, value.toBuildConfigField())
}

fun ProductFlavor.setStringArrayBuildConfigField(key: String, value: Array<String>) {
    val sanitizedValue = "{${value.joinToString { it.toBuildConfigField() }}}"
    this.buildConfigField("String[]", key, sanitizedValue)
}

fun ProductFlavor.setBooleanBuildConfigField(key: String, value: Boolean) {
    this.buildConfigField("boolean", key, value.toString())
}

fun ProductFlavor.setLongBuildConfigField(key: String, value: Long) {
    this.buildConfigField("long", key, value.toString())
}

fun ProductFlavor.setIntBuildConfigField(key: String, value: Int) {
    this.buildConfigField("int", key, value.toString())
}

fun ProductFlavor.setIntArrayBuildConfigField(key: String, value: Array<Int>) {
    val sanitizedValue = "{${value.joinToString()}}"
    this.buildConfigField("int[]", key, sanitizedValue)
}
//endregion

internal fun String.toBuildConfigField(): String {
    return "\"$this\""
}
