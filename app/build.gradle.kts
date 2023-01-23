import id.fakhri_khairi.versions.DependenciesPlugin
import id.fakhri_khairi.versions.Android
import id.fakhri_khairi.versions.BuildType
import id.fakhri_khairi.versions.Module

plugins {
    id("com.android.application")
    id("id.fakhri_khairi.versions")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

configurations.all {
    resolutionStrategy.force("junit:junit:4.13.2")
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

repositories {
    google()
    mavenCentral()
}

android {
    compileSdk = Android.SDK
    buildToolsVersion = Android.BUILD_TOOLS

    defaultConfig {
        applicationId = Android.APPLICATION_ID
        minSdk = Android.MIN_SDK
        targetSdk = Android.SDK
        versionCode = Android.VERSION_CODE
        versionName = Android.VERSION_NAME
        testInstrumentationRunner = Android.TEST_RUNNER

        multiDexEnabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled =  false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    viewBinding {
        android.buildFeatures.viewBinding = true
    }

    compileOptions {
        sourceCompatibility =  JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(Module.CORE))

    implementation(DependenciesPlugin.hiltAndroid)
    implementation(DependenciesPlugin.timber)
    implementation(DependenciesPlugin.multiDex)

    kapt(DependenciesPlugin.hiltAndroidCompiler)

    testImplementation(DependenciesPlugin.jUnit)

    androidTestImplementation(DependenciesPlugin.jUnitAndroid)
    androidTestImplementation(DependenciesPlugin.espresso)
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