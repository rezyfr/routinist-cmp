import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.routinist.kotlinMultiplatform)
    alias(libs.plugins.routinist.shared)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.sqldelightApp)
}

ktlint {
    android = true
    outputToConsole = true
    outputColorName = "RED"
}

kotlin {

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    jvm()

    js(IR) {
        browser()
    }

    sourceSets {

        commonTest {
            dependencies {

            }
        }
        commonMain {
            dependencies {
            }
        }

        androidMain {
            dependencies {

                implementation(libs.compose.ui.tooling)
                implementation(libs.compose.ui.tooling.preview)

            }
        }
        iosMain {
            dependencies {

            }
        }
        jvmMain {
            dependencies {

            }
        }

        jsMain {
            dependencies{
                implementation(devNpm("copy-webpack-plugin", "9.1.0"))
            }
        }

    }

    sqldelight {
        databases {
            create("RoutinistDatabase") {
                packageName = "id.rezyfr.routinist"
            }
        }
    }
}


