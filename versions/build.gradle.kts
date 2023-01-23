plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("versions") {
            id = "id.fakhri_khairi.versions"
            implementationClass = "id.fakhri_khairi.versions.DependenciesPlugin"
        }
    }
}
