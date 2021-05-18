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

    buildTypes.forEach {
        it.buildConfigField("String", "BASE_URL", "\"https://api.openweathermap.org/data/2.5/\"")
        it.buildConfigField("String", "API_KEY", "\"9d151f7cc8fa0a23c2b72d63a032976f\"")
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

    implementation(Dependencies.Kotlin.stdlib)
    implementation(Dependencies.GoogleService.location)
    implementation(Dependencies.Coroutines.coroutines)
    implementation(Dependencies.Gson.gson)
    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.gson)
    implementation(Dependencies.Retrofit.logging)
    implementation(Dependencies.Koin.koinAndroid)
    implementation(Dependencies.Koin.koinExt)
}