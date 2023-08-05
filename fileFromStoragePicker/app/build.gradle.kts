@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id ("kotlin-kapt")
}

android {
    namespace = "com.example.fileFromStoragePicker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.fileFromStoragePicker"
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
        kotlinCompilerExtensionVersion = "1.5.0"
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
    implementation(libs.androidx.documentfile)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    //Dagger - Hilt
    //noinspection UseTomlInstead
    implementation ("com.google.dagger:hilt-android:2.47")
    //noinspection UseTomlInstead
    kapt ("com.google.dagger:hilt-android-compiler:2.47")
    //noinspection UseTomlInstead
    kapt ("androidx.hilt:hilt-compiler:1.0.0")
    //noinspection UseTomlInstead
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0-alpha01")
    //noinspection UseTomlInstead
    implementation ("androidx.hilt:hilt-work:1.0.0")
    //noinspection UseTomlInstead
    implementation("androidx.localbroadcastmanager:localbroadcastmanager:1.1.0")
    //noinspection UseTomlInstead
    implementation("androidx.annotation:annotation:1.6.0")
    //noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    //noinspection UseTomlInstead
    implementation("androidx.savedstate:savedstate-ktx:1.2.1")

    //worker
    val coreVersion = "1.6.0"
    val workVersion = "2.7.1"
    //noinspection GradleDependency,UseTomlInstead
    implementation ("androidx.core:core-ktx:$coreVersion")
    //noinspection GradleDependency,UseTomlInstead
    implementation ("androidx.work:work-runtime-ktx:$workVersion")
    //noinspection UseTomlInstead
    implementation ("androidx.hilt:hilt-work:1.0.0")

    //noinspection UseTomlInstead
    implementation ("io.coil-kt:coil-compose:2.4.0")
    //noinspection UseTomlInstead
    implementation ("io.coil-kt:coil:2.4.0")

    //video player
    //noinspection UseTomlInstead
    implementation ("com.google.android.exoplayer:exoplayer:2.19.0")
    //noinspection UseTomlInstead
    implementation ("com.google.android.exoplayer:exoplayer-core:2.19.0")
    //noinspection UseTomlInstead
    implementation ("com.google.android.exoplayer:exoplayer-dash:2.19.0")
    //noinspection UseTomlInstead
    implementation ("com.google.android.exoplayer:exoplayer-ui:2.19.0")
}