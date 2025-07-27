pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "EconomyApp"
include(":app")
include(":core")
include(":core:ui")
include(":core:base")
include(":core:data")
include(":core:data:local")
include(":core:data:remote")
include(":core:domain")
include(":core:navigation")
include(":core:navigation:model")
include(":features")
include(":features:account")
include(":features:articles")
include(":features:settings")
include(":features:transactions")
