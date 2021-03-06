plugins {
//    id("com.android.dynamic-feature")
    id(BuildPlugin.checkUpdateDependency)
    id("com.android.library")
    id("kotlin-android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(Config.compileSdkVersion)

    defaultConfig {
        minSdkVersion(Config.minSdkVersion)
        targetSdkVersion(Config.targetSdkVersion)

        testInstrumentationRunner(Config.androidTestInstrumentation)
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
        it.buildConfigField("String", "IMAGE_URL", "\"https://api.openweathermap.org/img/w/\"")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures.apply {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":core"))
    implementation(Dependencies.Kotlin.stdlib)
    implementation(Dependencies.GoogleService.location)
    implementation(Dependencies.Coroutines.coroutines)
    implementation(Dependencies.Koin.koinExt)
    implementation(Dependencies.Koin.koinAndroid)
    implementation(Dependencies.Koin.koinViewModel)
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.Material.material)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.navFragment)
    implementation(Dependencies.AndroidX.navUi)
    implementation(Dependencies.Dexter.dexter)
    implementation(Dependencies.AndroidX.viewpager2)
    implementation(Dependencies.Glide.glide)
    kapt(Dependencies.Glide.glideCompiler)
    implementation(Dependencies.Gson.gson)
    implementation(Dependencies.Material.materialSearch)
}