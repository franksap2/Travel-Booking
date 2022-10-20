import Dependencies.Google.implementHilt
import Dependencies.Kotlin.implementCoroutines

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

apply {
    from("${rootProject.projectDir}/gradle/conventions/android-library-convention.gradle")
}

dependencies {

    implementHilt()
    implementCoroutines()

    testImplementation("junit:junit:4.13.2")
}