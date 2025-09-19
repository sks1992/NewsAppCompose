// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false   // Declares application plugin without applying it here
    alias(libs.plugins.kotlin.android) apply false        // Kotlin Android plugin
    alias(libs.plugins.kotlin.compose) apply false        // Kotlin Compose plugin
    alias(libs.plugins.devtools.ksp) apply false          // Kotlin Symbol Processing (KSP)
    alias(libs.plugins.hilt.plugin) apply false           // Hilt DI plugin
    alias(libs.plugins.plugin.serialization) apply false  // Serialization plugin
}