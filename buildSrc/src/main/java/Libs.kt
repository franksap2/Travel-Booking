import org.gradle.api.artifacts.dsl.DependencyHandler

object Versions {
    const val compileSdk = 33
    const val targetSdk = 33
    const val minSdk = 26
    const val kotlin_version = "1.7.10"
    const val gradle = "7.2.1"
    const val compose = "1.2.1"
    const val compose_compiler = "1.3.0"
    const val compose_activity = "1.5.1"
    const val compose_hilt = "1.0.0"
    const val androidx_core_kotlin = "1.8.0"
    const val appcompat = "1.5.0"
    const val lifecycle_version = "2.5.1"
    const val firebase = "30.3.1"
    const val nav_version = "2.5.1"
    const val hilt = "2.42"
    const val google_service = "4.3.13"
    const val coroutines = "1.6.4"
    const val coil = "2.2.1"
    const val accompanist = "0.25.1"
    const val serialization = "1.4.0"
    const val material = "1.6.1"
}

object Plugins {
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_version}"
    const val gradle_plugin = "com.android.tools.build:gradle:${Versions.gradle}"
    const val hilt_plugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
}

object Dependencies {

    object Compose {
        private const val compose_ui = "androidx.compose.ui:ui:${Versions.compose}"
        private const val compose_ui_tooling = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
        private const val compose_activity = "androidx.activity:activity-compose:${Versions.compose_activity}"
        private const val compose_foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
        private const val compose_material = "androidx.compose.material:material:${Versions.compose}"
        private const val compose_navigation = "androidx.navigation:navigation-compose:${Versions.nav_version}"
        private const val compose_hilt = "androidx.hilt:hilt-navigation-compose:${Versions.compose_hilt}"
        private const val coil_compose = "io.coil-kt:coil-compose:${Versions.coil}"

        fun DependencyHandler.apiCompose() {
            api(compose_ui)
            api(compose_ui_tooling)
            api(compose_activity)
            api(compose_foundation)
            api(compose_material)
            api(compose_navigation)
            api(compose_hilt)
            api(coil_compose)
        }


    }

    object Androidx {
        private const val core_ktx = "androidx.core:core-ktx:${Versions.androidx_core_kotlin}"
        private const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        private const val lifecycle = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_version}"
        private const val lifecycle_compose = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle_version}"

        fun DependencyHandler.implementAndroidX() {
            implementation(core_ktx)
            implementation(appcompat)
            implementation(lifecycle)
            implementation(lifecycle_compose)
        }
    }

    object Google {

        private const val hilt_android = "com.google.dagger:hilt-android:${Versions.hilt}"
        private const val hilt_compiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"

        const val material = "com.google.android.material:material:${Versions.material}"

        fun DependencyHandler.implementHilt() {
            implementation(hilt_android)
            add("kapt", hilt_compiler)
        }
    }

    object Kotlin {

        private const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        private const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        private const val coroutines_jvm = "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:${Versions.coroutines}"

        fun DependencyHandler.implementCoroutines() {
            implementation(coroutines_core)
            implementation(coroutines_jvm)
            implementation(coroutines_android)
        }
    }
}

fun DependencyHandler.implementation(dependency: Any) {
    add("implementation", dependency)
}

fun DependencyHandler.api(dependency: Any) {
    add("api", dependency)
}

