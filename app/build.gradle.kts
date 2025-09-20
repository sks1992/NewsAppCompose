plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.plugin.serialization)
    id("kotlin-parcelize")
}

android {
    namespace = "sk.sksv.newsappcompose"
    compileSdk = 36

    defaultConfig {
        applicationId = "sk.sksv.newsappcompose"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = listOf("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx) // Kotlin extensions for core Android APIs
    implementation(libs.androidx.lifecycle.runtime.ktx) // Lifecycle-aware coroutines and components
    implementation(libs.androidx.activity.compose) // Activity integration for Jetpack Compose
    implementation(platform(libs.androidx.compose.bom)) // Manage Compose versions via BOM
    implementation(libs.androidx.compose.ui) // Core Compose UI toolkit
    implementation(libs.androidx.compose.ui.graphics) // Graphics utilities for Compose
    implementation(libs.androidx.compose.ui.tooling.preview) // Preview support for Compose
    implementation(libs.androidx.compose.material3) // Material 3 components for Compose

    // Splash API
    implementation(libs.androidx.core.splashscreen) // Android 12+ splash screen compat library

    // Compose Navigation
    implementation(libs.androidx.navigation.compose) // Navigation framework for Compose UIs

    // Dagger Hilt
    implementation(libs.hilt.android) // Hilt runtime for dependency injection
    ksp(libs.hilt.compiler) // Hilt code generation (annotation processor)
    implementation(libs.androidx.hilt.navigation.compose) // Hilt helpers for Navigation + Compose

    // Retrofit Networking
    implementation(libs.retrofit) // Type-safe HTTP client
    implementation(libs.retrofit.gson.converter) // JSON serialization with Gson
    implementation(libs.logging.interceptor) // OkHttp logging interceptor for network calls

    // Coil
    implementation(libs.coil.compose) // Image loading for Compose

    // Datastore
    implementation(libs.androidx.datastore.preferences) // Key-value storage using DataStore Preferences

    // Compose Foundation
    implementation(libs.androidx.compose.foundation) // Foundational Compose building blocks

    // Accompanist
    implementation(libs.accompanist.systemuicontroller) // Control system bars (status/navigation)

    // Paging 3
    implementation(libs.androidx.paging.runtime) // Paging runtime for data pagination
    implementation(libs.androidx.paging.compose) // Compose integration for Paging lists

    // Room Database
    implementation(libs.room.runtime) // Room database runtime
    implementation(libs.room.ktx) // Kotlin extensions and coroutines support for Room
    implementation(libs.room.paging) // Paging integration with Room
    ksp(libs.room.compiler) // Room code generation (annotation processor)

    // Testing
    testImplementation(libs.junit) // Local unit tests
    androidTestImplementation(libs.androidx.junit) // AndroidX JUnit extensions for instrumentation tests
    androidTestImplementation(libs.androidx.espresso.core) // UI testing with Espresso
    androidTestImplementation(platform(libs.androidx.compose.bom)) // Align Compose test libs with BOM
    androidTestImplementation(libs.androidx.compose.ui.test.junit4) // Compose UI testing framework (JUnit4)

    // Debug tooling
    debugImplementation(libs.androidx.compose.ui.tooling) // Interactive tooling (preview/inspection)
    debugImplementation(libs.androidx.compose.ui.test.manifest) // Test manifest for Compose UI tests
}