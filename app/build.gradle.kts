plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
}

android {
    namespace = "com.backcube.economyapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.backcube.economyapp"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

// Modules
dependencies {
    kapt(libs.dagger.compiler)

    fun File.isGradleModule() = isDirectory && File(this, "build.gradle.kts").exists()

    fun collectModules(base: File, prefix: String = ""): List<String> {
        if (!base.exists()) return emptyList()
        return base.walkTopDown()
            .filter { it.isGradleModule() }
            .map { file ->
                val relativePath = file.relativeTo(rootDir).invariantSeparatorsPath
                ":" + relativePath.replace("/", ":")
            }
            .toList()
    }

    val modulePaths = listOf("core", "features")
        .flatMap { collectModules(File(rootDir, it)) }

    modulePaths.forEach { path ->
        implementation(project(path))
    }
}

// Libraries
dependencies {
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.work.runtime.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}