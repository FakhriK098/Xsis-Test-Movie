import id.fakhri_khairi.versions.DependenciesPlugin
import id.fakhri_khairi.versions.Android
import id.fakhri_khairi.versions.Module

plugins {
    id("com.android.library")
    id("id.fakhri_khairi.versions")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Android.SDK

    defaultConfig {
        minSdk = Android.MIN_SDK
        targetSdk = Android.SDK
        testInstrumentationRunner = Android.TEST_RUNNER

        val baseUrl: String by project
        val accessTokenV4: String by project

        buildConfigField(
            type = "String",
            name = "BASE_URL",
            value = baseUrl
        )

        buildConfigField(
            type = "String",
            name = "ACCESS_TOKEN_V4",
            value = "\"$accessTokenV4\""
        )
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    api(project(Module.DOMAIN))

    implementation(DependenciesPlugin.coreKtx)
    implementation(DependenciesPlugin.coroutinesCore)
    implementation(DependenciesPlugin.kotlin)
    implementation(DependenciesPlugin.kotlinStdLibJdk)
    implementation(DependenciesPlugin.hiltAndroid)
    implementation(DependenciesPlugin.crypto)
    implementation(DependenciesPlugin.moshiKotlin)
    implementation(DependenciesPlugin.retrofit)
    implementation(DependenciesPlugin.moshiConverter)
    implementation(DependenciesPlugin.loggingInterceptor)

    testImplementation(DependenciesPlugin.jUnit)
    testImplementation(DependenciesPlugin.jUnitAndroid)
    testImplementation(DependenciesPlugin.mockk)
    testImplementation(DependenciesPlugin.coroutinesTest)
    testImplementation(DependenciesPlugin.robolectric)

    kapt(DependenciesPlugin.hiltAndroidCompiler)
    kapt(DependenciesPlugin.moshiCodegen)
}