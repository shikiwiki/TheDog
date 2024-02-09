plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
//    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.example.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
//        buildConfigField("String", "API_KEY", "live_78uH11IyabCdKAj68Sf1exNbi2vyrC2zBBnl0D2m3gWvbYjMjemv4WTZbMWd8e43")
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
        buildConfig = true
    }
}

dependencies {

    implementation(project(":domain"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //    Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.logging.interceptor)

    // Room
    implementation (libs.androidx.room.runtime)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.room.compiler)
    implementation (libs.room.ktx)
    //    ...do I need it in data?
//    implementation(libs.androidx.legacy.support.v4)
}

//secrets {
//    // Change the properties file from the default "local.properties" in your root project
//    // to another properties file in your root project.
//    propertiesFileName = "secrets.properties"
//
//    // A properties file containing default secret values. This file can be checked in version
//    // control.
//    defaultPropertiesFileName = "secrets.defaults.properties"
//
//    // Configure which keys should be ignored by the plugin by providing regular expressions.
//    // "sdk.dir" is ignored by default.
//    ignoreList.add("keyToIgnore") // Ignore the key "keyToIgnore"
//    ignoreList.add("sdk.*")       // Ignore all keys matching the regexp "sdk.*"
//}