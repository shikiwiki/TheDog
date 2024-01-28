// Top-level build file where you can add configuration options common to all sub-projects/modules.
//buildscript {
//    repositories {
//        google()
//    }
//    dependencies {
//        classpath(libs.androidx.navigation.safe.args.gradle.plugin.v275)
//    }
//}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
}