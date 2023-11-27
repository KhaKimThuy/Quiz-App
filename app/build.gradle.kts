plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.afinal"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.afinal"
        minSdk = 28
        targetSdk = 33
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
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Circle image
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Flip cardview
    implementation("com.wajahatkarim:EasyFlipView:3.0.3")

    // Adapter
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.google.code.gson:gson:2.8.5")

    // Firebase
//    implementation("com.google.firebase:firebase-database-ktx:20.2.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0")
    implementation("com.firebaseui:firebase-ui-database:8.0.2")
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-storage-ktx")

    // To load image
    implementation("com.squareup.picasso:picasso:2.71828")

    // Progress Dialog Library
    implementation("com.jpardogo.googleprogressbar:library:1.2.0")

    // Image Picker
    implementation("com.github.dhaval2404:imagepicker:2.1")

    // Tablayout - Viewpager
    // viewpager2
    implementation ("androidx.viewpager2:viewpager2:1.0.0")
    //tablayout
    implementation ("com.google.android.material:material:1.3.0-alpha04")
    implementation("com.google.firebase:firebase-database:20.3.0")
//    gif
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.25")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.github.smarteist:autoimageslider:1.4.0")
    implementation("com.github.smarteist:autoimageslider:1.4.0-appcompat")
    implementation("com.github.bumptech.glide:glide:4.16.0")

}