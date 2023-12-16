object Versions {
    //Kotlin
    const val core_ktx = "1.12.0"

    //AndroidX
    const val appcompat = "1.6.1"

    //Design
    const val material = "1.10.0"

    //Coil
    const val coil = "2.4.0"

    //Koin
    const val koin = "3.5.0"

    //Retrofit
    const val retrofit = "2.9.0"

    //Navigation
    const val navigation = "2.7.5"
}

object Kotlin {
    const val core = "androidx.core:core-ktx:${Versions.core_ktx}"
}

object AndroidX {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
}

object Design {
    const val material = "com.google.android.material:material:${Versions.material}"
}

object Coil {
    const val coil_kt = "io.coil-kt:coil:${Versions.coil}"
}

object Koin {
    const val android = "io.insert-koin:koin-android:${Versions.koin}"
    const val core = "io.insert-koin:koin-core:${Versions.koin}"
    const val navigation = "io.insert-koin:koin-androidx-navigation:${Versions.koin}"
    const val androidx = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
}

object Retrofit {
    const val main = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson_convertor = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
}

object Navigation {
    const val fragment_ktx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
}