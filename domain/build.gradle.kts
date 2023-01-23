import id.fakhri_khairi.versions.Android

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("id.fakhri_khairi.versions")
    id("kotlin-parcelize")
}

android {
    compileSdk = Android.SDK

    defaultConfig {
        minSdk = Android.MIN_SDK
        targetSdk = Android.SDK
        testInstrumentationRunner = Android.TEST_RUNNER
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release")
        getByName("debug")
        create("staging")
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

dependencies { }

