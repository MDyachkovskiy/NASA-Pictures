import java.util.Properties

plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.gb_materialdesign"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gb_materialdesign"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        val properties = Properties().apply {
            load(project.rootProject.file("app.properties").inputStream())
        }
        val nasaApiKey = properties.getProperty("NASA_API_KEY", "")

        forEach { buildType ->
            buildType.buildConfigField("String", "NASA_API_KEY", "\"$nasaApiKey\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(project (":core"))
    implementation(project (":picture_of_the_day"))
    implementation(project (":earth_picture"))
    implementation(project (":remote_data"))

    //Kotlin
    implementation (Kotlin.core)

    //AndroidX
    implementation (AndroidX.appcompat)

    //Design
    implementation (Design.material)

    //Koin
    implementation (Koin.android)
    implementation (Koin.core)
    implementation (Koin.navigation)
    implementation (Koin.androidx)

    //Retrofit
    implementation (Retrofit.main)
    implementation (Retrofit.gson_convertor)
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")

    //Navigation
    implementation (Navigation.fragment_ktx)
    implementation (Navigation.ui_ktx)

    implementation ("com.github.javafaker:javafaker:1.0.2")

}