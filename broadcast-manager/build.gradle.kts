plugins {
    id("com.android.library")
    kotlin("android")
    `maven-publish`

}

android {
    namespace = "com.bitnic.broadcast_manager"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
//    kotlinOptions {
//        jvmTarget = "11"
//    }
}

dependencies {

    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

//afterEvaluate {
//
//    //gradlew assembleRelease
//    val versionName = "1.0.0"
//    val libName = "bitnicbroadcastmanager"
//
//    // 🔹 основная задача AAR
//    val releaseAar = tasks.named("bundleReleaseAar")
//
//    // 🔹 создаём source.jar с тем же именем и версией
//    val sourceJar = tasks.register<Jar>("sourceJar") {
//        group = "build"
//        archiveBaseName.set(libName)
//        archiveVersion.set(versionName)
//        archiveClassifier.set("sources")
//
//        from(android.sourceSets["main"].java.srcDirs)
//        from("src/main/java")
//
//        destinationDirectory.set(file("${layout.buildDirectory.get()}/outputs/aar"))
//    }
//
//    // 🔹 переименовываем AAR после сборки (чтобы версия была в имени)
//    releaseAar.configure {
//        doLast {
//            val outputDir = file("${layout.buildDirectory.get()}/outputs/aar")
//            val originalAar = outputDir.listFiles()?.find { it.name.endsWith(".aar") }
//            if (originalAar != null) {
//                val targetFile = File(outputDir, "$libName-$versionName.aar")
//                originalAar.renameTo(targetFile)
//                println("✅ AAR renamed to: ${targetFile.name}")
//            }
//        }
//        finalizedBy(sourceJar)
//    }
//}
publishing {
    publications {
        create<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"])
            }

            groupId = "com.github.ionson100" // 👈 твой GitHub username
            artifactId = "bitnicbroadcastmanager"          // 👈 имя библиотеки
            version = "1.0.4"                  // 👈 версия тега
        }
    }
}