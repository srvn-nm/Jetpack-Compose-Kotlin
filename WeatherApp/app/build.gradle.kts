plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.example.weatherApp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.weatherApp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //okhttp for json
    //noinspection UseTomlInstead
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")
    //noinspection UseTomlInstead
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")
    //retrofit
    //noinspection UseTomlInstead
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    //moshi
    //noinspection UseTomlInstead
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")
    //noinspection UseTomlInstead
    implementation ("com.squareup.moshi:moshi:1.15.0")
    //kotlin moshi json Adapter
    //noinspection UseTomlInstead
    implementation ("com.squareup.moshi:moshi-kotlin:1.15.0")

    //navigation in compose
    //noinspection UseTomlInstead
    implementation ("androidx.navigation:navigation-compose:2.7.6")

    //material
    //noinspection UseTomlInstead
    implementation ("com.google.android.material:material:1.11.0")

    //Gson
    //noinspection UseTomlInstead
    implementation("com.google.code.gson:gson:2.10.1")

    //spinner
    //noinspection UseTomlInstead
    implementation("androidx.compose.runtime:runtime-livedata:1.5.4")
}