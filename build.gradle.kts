import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        val androidToolsBuildGradle = "com.android.tools.build:gradle:7.1.0"
        val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.20"
        val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:2.40.5"
        val detektGradlePlugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.19.0"

        classpath(androidToolsBuildGradle)
        classpath(kotlinGradlePlugin)
        classpath(hiltGradlePlugin)
        classpath(detektGradlePlugin)
    }
}

allprojects {
    configurations.all {
        resolutionStrategy.eachDependency {
            if ("org.jacoco" == requested.group) {
                useVersion("0.8.7")
            }
        }
    }
}

apply("jacoco.gradle.kts")

dependencies {
    "detektPlugins"("io.gitlab.arturbosch.detekt:detekt-formatting:1.19.0")
}

plugins {
    id("io.gitlab.arturbosch.detekt").version("1.19.0")
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

tasks.register(name = "clean", type = Delete::class) {
    delete(rootProject.buildDir)
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        html.required.set(true)
        txt.required.set(true)
        xml.required.set(false)
        sarif.required.set(false)
    }
}

// Kotlin DSL
tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = JvmTarget.JAVA_8
}

tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
    jvmTarget = JvmTarget.JAVA_8
}

// config JVM target to 1.8 for kotlin compilation tasks
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = JvmTarget.JAVA_8
}

// Run multiple test cases in parallel
tasks.withType<Test>().configureEach {
    maxParallelForks = ParallelForks.MAX
}

// Run the compiler as a separate process
tasks.withType<JavaCompile>().configureEach {
    options.isFork = true
}

object JvmTarget {
    const val JAVA_8 = "1.8"
}

object ParallelForks {
    const val MAX = 8
}