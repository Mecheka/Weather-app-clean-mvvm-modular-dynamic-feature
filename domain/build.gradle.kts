plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdkVersion(Config.compileSdkVersion)

    defaultConfig {
        minSdkVersion(Config.minSdkVersion)
        targetSdkVersion(Config.targetSdkVersion)

        testInstrumentationRunner(Config.androidTestInstrumentation)
        consumerProguardFiles( "consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
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
    implementation(project(":core"))
    implementation(Dependencies.Kotlin.stdlib)
    implementation(Dependencies.GoogleService.location)
    implementation(Dependencies.Coroutines.coroutines)
    implementation(Dependencies.Koin.koinExt)
    implementation(Dependencies.Koin.koinAndroid)
}