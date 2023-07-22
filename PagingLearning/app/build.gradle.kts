@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id ("kotlin-kapt")
}

android {
    namespace = "com.example.paginglearning"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.paginglearning"
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
        kotlinCompilerExtensionVersion = "1.4.3"
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

    //retrofit
    //noinspection UseTomlInstead
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    //noinspection UseTomlInstead
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //okhttp
    //noinspection UseTomlInstead
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    //noinspection UseTomlInstead
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    //coil image
    //noinspection UseTomlInstead
    implementation("com.google.accompanist:accompanist-coil:0.12.0")


    //pagination
    //noinspection UseTomlInstead
    implementation("androidx.paging:paging-compose:3.2.0-rc01")
    val pagingVersion = "3.1.1"
    //noinspection UseTomlInstead
    implementation ("androidx.paging:paging-runtime-ktx:$pagingVersion")

    val hiltVersion = "2.44"
    //noinspection GradleDependency,UseTomlInstead
    implementation ("com.google.dagger:hilt-android:$hiltVersion")
    //noinspection KaptUsageInsteadOfKsp,UseTomlInstead
    kapt ("com.google.dagger:hilt-compiler:$hiltVersion")
    //noinspection UseTomlInstead
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")
}