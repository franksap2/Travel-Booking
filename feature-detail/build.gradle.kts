import Dependencies.Androidx.implementAndroidX
import Dependencies.Google.implementHilt

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

apply {
    from("${rootProject.projectDir}/gradle/conventions/android-library-convention.gradle")
    from("${rootProject.projectDir}/gradle/conventions/compose-convention.gradle")
}

dependencies {

    implementation(project(":core-ui"))
    implementation(project((":data-places")))
    implementation(project((":feature-calendar")))
    implementAndroidX()
    implementHilt()

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}