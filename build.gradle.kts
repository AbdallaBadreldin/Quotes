
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        //use the latest version of gradle of kotlin Gradle plugin
        classpath ("com.android.tools.build:gradle:7.4.2")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")

        val nav_version = "2.7.6"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.44")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    // hilt plugin
    id ("com.google.dagger.hilt.android") version "2.44" apply false
    //plugins for Serializable annotation using in type converter
    id ("org.jetbrains.kotlin.jvm") version "1.7.20" apply false
}
