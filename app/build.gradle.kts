import Dependencies.Androidx.implementAndroidX
import Dependencies.Google.implementHilt

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}
apply(plugin = "kotlin-android")

apply {
    from("${rootProject.projectDir}/gradle/conventions/android-library-convention.gradle")
    from("${rootProject.projectDir}/gradle/conventions/compose-convention.gradle")
}

android {
    defaultConfig {
        applicationId = "com.franksap2.travelbooking"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        signingConfig = signingConfigs.getByName("debug")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packagingOptions {
        resources.excludes += "META-INF/AL2.0"
        resources.excludes += "META-INF/LGPL2.1"
        resources.excludes += "DebugProbesKt.bin"
    }
}

dependencies {

    implementAndroidX()
    implementHilt()
    implementation(project(":feature-onboarding"))
    implementation(project(":feature-home"))
    implementation(project(":feature-detail"))
    implementation(project(":core-ui"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.2.1")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.2.1")

}


