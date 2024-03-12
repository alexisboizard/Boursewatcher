plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")

}

android {

    namespace = "com.alexisboiz.boursewatcher"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.alexisboiz.boursewatcher"
        minSdk = 24
        targetSdk = 33
        versionCode = 10
        versionName = "1.6"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildFeatures{
            buildConfig = true
        }
    }

    flavorDimensions += "env"
    productFlavors {
        create("dev") {
            dimension = "env"
            versionNameSuffix = "-dev"
        }
        create("ppr") {
            dimension = "env"
            versionNameSuffix = "-ppr"
        }
        create("prod") {
            dimension = "env"
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
    allprojects {
        repositories {
            google()
            mavenCentral()
            maven(url="https://jitpack.io")
        }
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.databinding:library:3.2.0-alpha11")
    implementation("com.google.firebase:firebase-messaging:23.3.1")
    implementation("androidx.media3:media3-common:1.1.0")
    implementation("com.google.firebase:firebase-messaging-ktx:23.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.1.0")

    //Coroutine
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    implementation("androidx.fragment:fragment-ktx:1.6.1")
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Chart library
    implementation("com.github.AAChartModel:AAChartCore-Kotlin:7.2.1")

    //Swipe to refresh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
    implementation("com.google.firebase:firebase-storage")
    implementation("com.firebaseui:firebase-ui-storage:7.2.0")
    // Firebase - Crashlytics
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")
    // Firebase - Realtime Database
    implementation("com.google.firebase:firebase-database")
    // Firebase - Firestore
    implementation("com.google.firebase:firebase-firestore-ktx")



    // Room
    val room_version = "2.5.0"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("io.reactivex.rxjava2:rxjava:2.2.16")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")

    // QR Code
    implementation("io.github.g0dkar:qrcode-kotlin-android:3.3.0")
    implementation("com.google.mlkit:barcode-scanning:17.2.0")


    //Scan QR Code
    implementation("com.google.android.gms:play-services-vision:20.1.3")

    //Picasso
    implementation("com.squareup.picasso:picasso:2.8")
    // CameraX library
    val camerax_version = "1.0.1"
    implementation("androidx.camera:camera-camera2:$camerax_version")
    implementation("androidx.camera:camera-lifecycle:$camerax_version")
    implementation("androidx.camera:camera-view:1.0.0-alpha30")

    // Easy Permissions
    implementation("pub.devrel:easypermissions:3.0.0")

    // GLIDE
    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation("com.google.android.material:material:<version>")
}