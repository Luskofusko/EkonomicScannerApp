plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
}

android {
    namespace = "com.example.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":domain"))

    // Room Db
    implementation(libs.androidx.room.runtime)
    kapt("androidx.room:room-compiler:${libs.versions.room.get()}")
    implementation(libs.androidx.room.ktx)

    // Hilt DI
    implementation(libs.hilt.android)
    kapt("com.google.dagger:hilt-android-compiler:${libs.versions.hilt.get()}")
    implementation(libs.javapoet)

    // Testing
    testImplementation(libs.junit)
    // Room Testing
    testImplementation(libs.androidx.room.testing)

    // Hilt Testing
    testImplementation(libs.hilt.android.testing)

    // Coroutines for testing
    testImplementation(libs.kotlinx.coroutines.test)

    // Mockito
    testImplementation(libs.mockito.core)
}
