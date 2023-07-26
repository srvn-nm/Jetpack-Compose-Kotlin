@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.stopWatch"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.stopWatch"
        minSdk = 24
        //noinspection OldTargetApi
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    //Dagger-Hilt
    //noinspection UseTomlInstead
    implementation("com.google.dagger:hilt-android:2.47")
    //noinspection UseTomlInstead
    kapt("com.google.dagger:hilt-android-compiler:2.47")
    //noinspection UseTomlInstead
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0-alpha01")
    //noinspection UseTomlInstead
    implementation("androidx.savedstate:savedstate-ktx:1.2.1")
    //this line fixed the apps keeps crashing
    //noinspection UseTomlInstead
    implementation ("androidx.navigation:navigation-compose:2.6.0")

    //viewModel
    //noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    //noinspection UseTomlInstead
    implementation("androidx.annotation:annotation:1.6.0")
    //noinspection UseTomlInstead
    implementation("androidx.localbroadcastmanager:localbroadcastmanager:1.1.0")

    //icons
    //noinspection UseTomlInstead
    implementation ("androidx.compose.material:material-icons-core:1.4.3")
    //noinspection UseTomlInstead
    implementation ("androidx.compose.material:material-icons-extended:1.4.3")
}