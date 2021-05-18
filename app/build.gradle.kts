import com.android.builder.internal.ClassFieldImpl

plugins {
    id(BuildPlugin.checkUpdateDependency)
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}


android {
    compileSdkVersion(Config.compileSdkVersion)
    buildToolsVersion(Config.buildToolsVersion)

    defaultConfig {
        applicationId(Config.applicationId)
        minSdkVersion(Config.minSdkVersion)
        targetSdkVersion(Config.targetSdkVersion)
        versionCode(Config.versionCode)
        versionName(Config.versionName)

        testInstrumentationRunner(Config.androidTestInstrumentation)
    }

    buildTypes {

        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
        }
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

    dynamicFeatures = mutableSetOf(":features:home")
}

dependencies {

    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.Kotlin.stdlib)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.Material.material)
    implementation(Dependencies.Dexter.dexter)
    implementation(Dependencies.GoogleService.location)
    implementation(Dependencies.Coroutines.coroutines)
    implementation(Dependencies.Glide.glide)
    implementation("androidx.navigation:navigation-dynamic-features-fragment:2.3.5")
    kapt(Dependencies.Glide.glideCompiler)
    implementation(Dependencies.Koin.koinAndroid)
    implementation(Dependencies.Koin.koinExt)
    implementation(Dependencies.AndroidX.navFragment)
    implementation(Dependencies.AndroidX.navUi)
}