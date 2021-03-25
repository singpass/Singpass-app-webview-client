package sg.ndi.build

import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.add
import org.gradle.kotlin.dsl.exclude

private fun ExternalModuleDependency.configure(setTransitive: Boolean? = null, setExclusions: List<Pair<String, String>>?) {
    if (setTransitive != null)
        isTransitive = setTransitive
    setExclusions?.forEach {
        exclude(group = it.first, module = it.second)
    }
}

internal fun DependencyHandler.configure(
    configuration: String,
    depNamePath: Any,
    setTransitive: Boolean? = null,
    setExclusions: List<Pair<String, String>>? = null
) {
    if (depNamePath is String) {

        add(configuration, depNamePath) {
            configure(setTransitive, setExclusions)
        }
    } else {
        add(configuration, depNamePath)
    }
}

internal fun String.combinePrefix(vararg appends: String): String {

    val sb = StringBuilder()
    sb.append(this)
    appends.forEach { s ->
        sb.append(s.capitalize())
    }

    return sb.toString()
}
