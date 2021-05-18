object Dependencies {

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    }

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val navFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.nav}"
        const val navUi = "androidx.navigation:navigation-ui-ktx:${Versions.nav}"
    }

    object Material {
        const val material = "com.google.android.material:material:${Versions.material}"
    }

    object Dexter {
        const val dexter = "com.karumi:dexter:${Versions.dexter}"
    }

    object GoogleService {
        const val location = "com.google.android.gms:play-services-location:${Versions.gmsLocation}"
    }

    object Coroutines {
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val gson ="com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        const val logging = "com.squareup.okhttp3:logging-interceptor:4.2.1"
    }

    object Gson {
        const val gson = "com.google.code.gson:gson:${Versions.gson}"
    }

    object Glide {
        const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
        const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    }

    object Koin {
        const val koinAndroid = "io.insert-koin:koin-android:${Versions.koin}"
        const val koinExt = "io.insert-koin:koin-androidx-ext:${Versions.koin}"
        const val koinViewModel = "io.insert-koin:koin-androidx-viewmodel:${Versions.koin}"
    }
}