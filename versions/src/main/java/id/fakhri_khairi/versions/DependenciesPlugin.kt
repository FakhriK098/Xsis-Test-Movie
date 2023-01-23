package id.fakhri_khairi.versions

import org.gradle.api.Plugin
import org.gradle.api.Project

class DependenciesPlugin : Plugin<Project> {
    override fun apply(target: Project) {}

    companion object {
        // Kotlin
        private const val kotlinVersion = "1.5.21"
        const val kotlinStdLibJdk = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"

        // AndroidX
        private const val navVersion = "2.3.5"
        private const val lifecycleVersion = "2.4.0"
        const val coreKtx = "androidx.core:core-ktx:1.7.0"
        const val appCompat = "androidx.appcompat:appcompat:1.4.1"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.3"
        const val multiDex = "androidx.multidex:multidex:2.0.1"
        const val crypto = "androidx.security:security-crypto:1.1.0-alpha01"
        const val legacySupport = "androidx.legacy:legacy-support-v4:1.0.0"
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$navVersion"
        const val navigationUI = "androidx.navigation:navigation-ui-ktx:$navVersion"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.2.1"
        const val lifeCycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
        const val lifeCycleCompiler = "androidx.lifecycle:lifecycle-compiler:$lifecycleVersion"

        // Jake Wharton
        const val timber = "com.jakewharton.timber:timber:5.0.1"

        // Hilt
        private const val hiltVersion = "2.40.5"
        const val hiltAndroid = "com.google.dagger:hilt-android:$hiltVersion"
        const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"

        // API
        private const val moshiVersion = "1.13.0"
        const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:$moshiVersion"
        const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion"
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.3"
        const val moshiConverter = "com.squareup.retrofit2:converter-moshi:2.4.0"

        // UI
        const val materialDesign = "com.google.android.material:material:1.5.0"
        const val coil = "io.coil-kt:coil:1.4.0"
        const val lottie = "com.airbnb.android:lottie:5.1.1"
        const val shimmer = "com.facebook.shimmer:shimmer:0.5.0"

        // Test
        private const val testCoreVersion = "1.4.0"
        private const val jUnitVersion = "1.1.3"
        const val jUnit = "junit:junit:4.13.2"
        const val jUnitAndroid = "androidx.test.ext:junit:$jUnitVersion"
        const val jUnitAndroidKtx = "androidx.test.ext:junit-ktx:$jUnitVersion"
        const val testCore = "androidx.test:core:$testCoreVersion"
        const val testCoreKtx = "androidx.test:core-ktx:$testCoreVersion"
        const val coreTesting = "androidx.arch.core:core-testing:2.1.0"
        const val mockk = "io.mockk:mockk:1.12.2"
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
        const val robolectric = "org.robolectric:robolectric:4.4"
    }
}
