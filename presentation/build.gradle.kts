plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
//    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.thedog"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.thedog"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        viewBinding = true
    }
}

dependencies {

    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

//    Dagger
    implementation("com.google.dagger:dagger:2.50")
    implementation("com.google.dagger:hilt-android:2.50")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("androidx.hilt:hilt-navigation-fragment:1.2.0-alpha01")

//    implementation(libs.hilt.android)
//    implementation(libs.androidx.hilt.navigation)
//    kapt(libs.hilt.compiler)
//    kapt(libs.androidx.hilt.compiler)

//    RecyclerView
    implementation(libs.androidx.recyclerview)

//    Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Architectural Components
    implementation(libs.androidx.lifecycle.viewmodel)

    // Coroutines
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.jetbrains.kotlinx.coroutines.android)

    // Room
    implementation (libs.androidx.room.runtime)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.room.compiler)

    // Coroutine Lifecycle Scopes
    implementation (libs.androidx.lifecycle.viewmodel.ktx.v270)
    implementation (libs.lifecycle.runtime.ktx)

    // Glide
    implementation (libs.glide)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.glide.compiler)

//    ...do I need it in presentation?
    implementation(libs.androidx.legacy.support.v4)

}