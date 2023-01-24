import id.fakhri_khairi.versions.Android
import id.fakhri_khairi.versions.DependenciesPlugin
import id.fakhri_khairi.versions.Module

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("id.fakhri_khairi.versions")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    compileSdk = Android.SDK

    defaultConfig {
        minSdk = Android.MIN_SDK
        targetSdk = Android.SDK
        testInstrumentationRunner = Android.TEST_RUNNER
        consumerProguardFiles("consumer-rules.pro")

        val youtubeToken: String by project

        buildConfigField(
            type = "String",
            name = "YOUTUBE_TOKEN",
            value = youtubeToken
        )
    }

    buildTypes {
        debug {
            isTestCoverageEnabled = true
            isMinifyEnabled = false
            extra.set("enableCrashlytics", false)
        }

        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    viewBinding {
        android.buildFeatures.viewBinding = true
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
    api(project(Module.DATA))

    api(DependenciesPlugin.coreKtx)
    api(DependenciesPlugin.lifeCycleViewModel)
    api(DependenciesPlugin.lifeCycleCompiler)
    api(DependenciesPlugin.materialDesign)
    api(DependenciesPlugin.timber)
    api(DependenciesPlugin.appCompat)
    api(DependenciesPlugin.constraintLayout)
    api(DependenciesPlugin.navigationFragment)
    api(DependenciesPlugin.navigationUI)
    api(DependenciesPlugin.legacySupport)
    api(DependenciesPlugin.coil)
    api(DependenciesPlugin.lottie)
    api(DependenciesPlugin.shimmer)

    testApi(DependenciesPlugin.jUnit)
    testApi(DependenciesPlugin.mockk)
    testApi(DependenciesPlugin.coroutinesTest)

    implementation(DependenciesPlugin.hiltAndroid)
    kapt(DependenciesPlugin.hiltAndroidCompiler)

    androidTestApi(DependenciesPlugin.jUnitAndroid)
    androidTestApi(DependenciesPlugin.espresso)

    implementation(files("libs/YouTubeAndroidPlayerApi.jar"))
}

kapt {
    correctErrorTypes = true

    javacOptions {
        // These options are normally set automatically via the Hilt Gradle plugin, but we
        // set them manually to workaround a bug in the Kotlin 1.5.20
        option("-Adagger.fastInit=ENABLED")
        option("-Adagger.hilt.android.internal.disableAndroidSuperclassValidation=true")
    }
}
