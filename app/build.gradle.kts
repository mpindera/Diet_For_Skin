plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("kotlin-kapt")
  id("com.google.gms.google-services")
}

android {
  namespace = "com.example.dietforskin"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.example.dietforskin"
    minSdk = 24
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
        getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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

  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
  implementation("androidx.activity:activity-compose:1.8.1")
  implementation(platform("androidx.compose:compose-bom:2023.10.01"))
  implementation("androidx.compose.ui:ui")
  implementation("androidx.compose.ui:ui-graphics")
  implementation("androidx.compose.ui:ui-tooling-preview")
  implementation("androidx.compose.material3:material3")
  implementation("com.google.firebase:firebase-auth:22.3.0")
  implementation("com.google.firebase:firebase-firestore:24.9.1")
  implementation("androidx.webkit:webkit:1.9.0")
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.5")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
  androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
  androidTestImplementation("androidx.compose.ui:ui-test-junit4")
  debugImplementation("androidx.compose.ui:ui-tooling:1.5.4")
  debugImplementation("androidx.compose.ui:ui-test-manifest")
  implementation("androidx.core:core-splashscreen:1.0.1")

  //ROOM
  implementation("androidx.room:room-runtime:2.5.0")
  implementation("androidx.room:room-ktx:2.5.0")
  kapt("androidx.room:room-compiler:2.5.0")

  //Navigation
  implementation("androidx.navigation:navigation-compose:2.7.5")

  //COIL
  implementation("io.coil-kt:coil-compose:2.0.0-rc01")

  //ANIMATION
  implementation("androidx.compose.animation:animation:1.6.0-beta02")

  //FIREBASE
  implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
  implementation("com.google.firebase:firebase-analytics")

  //FIREBASE_AUTH
  implementation("com.google.firebase:firebase-auth:22.3.0")
  // FirebaseUI for Firebase Realtime Database
  implementation("com.firebaseui:firebase-ui-database:8.0.2")

  // FirebaseUI for Cloud Firestore
  implementation("com.firebaseui:firebase-ui-firestore:8.0.2")

  // FirebaseUI for Firebase Auth
  implementation("com.firebaseui:firebase-ui-auth:8.0.2")

  // FirebaseUI for Cloud Storage
  implementation("com.firebaseui:firebase-ui-storage:8.0.2")
  implementation("io.insert-koin:koin-android:3.2.0")
  implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
  implementation("com.google.firebase:firebase-appcheck-playintegrity")


}
